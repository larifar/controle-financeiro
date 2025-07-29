package bot;

import bot.application.usecase.IGetListTransactionsUC;
import bot.application.usecase.ISaveTransactionUC;
import bot.domain.dto.TransactionDto;
import bot.domain.dto.TransactionEnums;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

public class MyBot extends AbilityBot{
    private final TelegramClient telegramClient;
    private final ISaveTransactionUC saveTransactionUC;
    private final IGetListTransactionsUC getListTransactionsUC;

    public MyBot(TelegramClient client, ISaveTransactionUC saveTransactionUC, IGetListTransactionsUC getListTransactionsUC) {
        super(client, "ControleFinanceiro_larifar_bot");
        telegramClient = client;
        this.saveTransactionUC = saveTransactionUC;
        this.getListTransactionsUC = getListTransactionsUC;
    }

    @Override
    public long creatorId() {
        return Long.parseLong(System.getenv("CREATOR_ID"));
    }

    private String showDefaultMessage(){
        return "Olá! Irei ser seu assistente financeiro!\n"
                + "Digite /help para ver minhas habilidades ";
    }

    private String showAbilities(){
        return "Essas são minhas habilidades:\n"+
                "- /help : mostro minhas habilidades\n"+
                "- /start : se for sua primeira vez\n"+
                "- /add : para adicionar um gasto ou receita\n" +
                "- /dia : para ver o resumo financeiro do seu dia\n"+
                "- /mes : para ver o resumo financeiro do seu mês\n";
    }

    public Ability help(){
        return Ability
                .builder()
                .name("help")
                .info("Mostra habilidades")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send(showAbilities(), ctx.chatId()))
                .build();
    }

    public Ability defaultMessage(){
        return Ability
                .builder()
                .name(DEFAULT)
                .flag(Flag.MESSAGE)
                .info("Mensagem inicial")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> silent.send(showDefaultMessage(), ctx.chatId()))
                .build();
    }

    public Ability add(){
        return Ability
                .builder()
                .name("add")
                .info("Adicionar Gasto/Receita")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx ->{
                    String text = String.join(" ", ctx.arguments());
                    String msgToUser = saveTransactionUC.save(text, ctx.chatId());
                    silent.send(msgToUser
                        , ctx.chatId());})
                .build();
    }

    public Ability dia(){
        return Ability
                .builder()
                .name("dia")
                .info("Resumo financeiro do seu dia")
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx ->{

                    List<TransactionDto> list = getTransactionsDay(ctx.chatId(), TransactionEnums.GASTO);


                    String msgToUser = list == null ? "Não há transações nesse dia" : list.toString();
                    silent.send(msgToUser
                            , ctx.chatId());})
                .build();
    }

    private List<TransactionDto> getTransactionsDay(long telegramId, TransactionEnums type){

        try{
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime from = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 0, 0);
            LocalDateTime until = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 23, 59, 59);

            return getListTransactionsUC.get(from, until, telegramId, type);
        } catch (MalformedURLException | JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

}
