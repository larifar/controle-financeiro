package bot;

import bot.application.usecase.ISaveTransactionUC;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class MyBot extends AbilityBot{
    private final TelegramClient telegramClient;
    private final ISaveTransactionUC saveTransactionUC;

    public MyBot(TelegramClient client, ISaveTransactionUC saveTransactionUC) {
        super(client, "ControleFinanceiro_larifar_bot");
        telegramClient = client;
        this.saveTransactionUC = saveTransactionUC;
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
                    String text = String.join(" ", ctx.arguments());
                    String msgToUser = saveTransactionUC.save(text, ctx.chatId());
                    silent.send(msgToUser
                            , ctx.chatId());})
                .build();
    }

}
