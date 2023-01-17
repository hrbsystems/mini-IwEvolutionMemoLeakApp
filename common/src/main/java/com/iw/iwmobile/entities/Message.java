package com.iw.iwmobile.entities;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextArea;
import com.codename1.ui.plaf.Border;
import com.codename1.util.StringUtil;
import com.iw.iwmobile.IwColorUtil;

import java.util.HashMap;
//import javafx.scene.control.Alert;

/**
 *
 * @author Marcos
 */
public class Message {

    public static final int OK_OPTION = 1;
    public static final int OK_CANCEL_OPTION = 2;
    public static final int YES_NO_OPTION = 3;

    public static final int INFORMATION_TYPE = 1;
    public static final int CONFIRMATION_TYPE = 2;
    public static final int ERROR_TYPE = 3;
    public static final int ATTENTION_TYPE = 4;

    public static final String OK = "Ok";
    public static final String CANCEL = "Cancelar";
    public static final String YES = "Sim";
    public static final String NO = "Não";

    public static final String INFORMATION = "Informação";
    public static final String CONFIRMATION = "Confirmação";
    public static final String ERROR = "Erro";
    public static final String ATTENTION = "Atenção";

    private static final HashMap<Integer, String> hmMsg = new HashMap<>();

    static {

        hmMsg.put(1145, "Assinatura digital realizada com sucesso.");

        hmMsg.put(1146, "Assinatura digital verificada com sucesso.");

        hmMsg.put(1152, "Falha na assinatura digital, verifique a descrição do erro.");

        hmMsg.put(6001, "Não houve alterações.");

        hmMsg.put(6003, "Confirma a exclusão do(s) item(s) ?");
        
        hmMsg.put(6005, "Operação bem sucedida.");

        hmMsg.put(6018, "O(s) seguinte(s) campo(s) deve(m) ser preenchido(s): %s1");

        hmMsg.put(6020,
                "Esse código foi removido, ou não foi cadastrado\n"
                + "corretamente como material ou o material foi inativado.");

        hmMsg.put(6054,
                "O(s) seguinte(s) campo(s) requerido(s)\n"
                + "precisa(m) ser valorado(s):\n"
                + "%s1");

        hmMsg.put(6108, "%s1 não pode ser maior que %s2");

        hmMsg.put(6115, "Item não permitido por contrato. Escolha uma das opções disponíveis:\n"
                + "\n"
                + "[ 1 ]   Refazer a pesquisa pelo farmaco '%s1' para localizar um item válido.\n"
                + "\n"
                + "[ 2 ]   Retornar à lista para selecionar outro item.\n"
                + "\n"
                + "[ 3 ]   Adicionar o item selecionado à prescrição (mesmo não autorizado).");

        hmMsg.put(6200, "%s1 não encontrado(a).");

        hmMsg.put(6204, "Dado inválido: %s1");

        hmMsg.put(6355, "Não é possível realizar alterações sobre prescrição já liberada ou que já tenham o processo de dispensação iniciado.Estorne a liberação da prescrição ou solicite o desbloqueio do processo de dispensação  antes de salvar as alterações.");

        hmMsg.put(6358, "Marca deve ser especificada.");

        hmMsg.put(6359, "Quantidade deve ser  maior que %s1");

        hmMsg.put(6355, "Não é possível realizar alterações sobre prescrição já liberada ou \n"
                + "que já tenham o processo de dispensação iniciado.\n"
                + "\n"
                + "Estorne a liberação da prescrição ou solicite o desbloqueio do processo \n"
                + "de dispensação  antes de salvar as alterações.");

        hmMsg.put(6368,
                "A prescrição foi alterada. Salve antes de re-ordenar.");

        hmMsg.put(6369,
                "Reordenar prescrição ?");

        hmMsg.put(6370,
                "Os itens relacionados a seguir, são de Controle Antimicrobiano \n"
                + "e/ou Tipo de controle por nro de dias de aplicação.\n"
                + "%s1\n"
                + "Informe a coluna Nro Dias Tratamento.");

        hmMsg.put(6371,
                "Existem itens inativados e/ou cancelados na prescrição.\n"
                + "Faça a correção antes de salvar.\n"
                + "Inativados:\n%s1\n"
                + "Cancelados:\n%s2");

        hmMsg.put(6372, "Novo itens precisam ser salvos.\n"
                + "Tente novamente mais tarde.");

        hmMsg.put(6398,
                "O medicamento selecionado não está liberado por contrato.\n"
                + "Selecione um item da lista apresentada.");

        hmMsg.put(6479,
                "Campo 'Justificativa Prescrição Emergencial' obrigatório.\n"
                + "Preencha o campo e efetue a salva novamente.");

        hmMsg.put(6480,
                "Existem medicamentos ainda sem \n"
                + "associação com um evento de CCID. \n"
                + "Não é possível concluir a salva do\n"
                + "controle de  CCID nessa condição.");

        hmMsg.put(6509,
                "Existe uma prescricao gerada previamente que ainda está editável.\n"
                + "Você deverá editar  essa prescrição ao invés de gerar \n"
                + "uma nova prescrição. "
        //                + "O sistema irá navegar automaticamente\n"
        //                + "para  essa prescrição para que você possa editá-la.\n"
        //                + "Nota: As datas de vigência dessa prescrição já serão \n"
        //                + "alteradas para estarem de  acordo com as datas de prorrogação \n"
        //                + "do regime de dispensação desse paciente."
        );

        hmMsg.put(6510,
                "Acione essa opção em caso de necessidades\n"
                + "de alteração na prescrição do paciente em\n"
                + "caráter de urgência / emergência.");

        hmMsg.put(6511,
                "Acione essa opção para gerar a prescrição de\n"
                + "implantação ou prorrogação do atendimento\n"
                + "do paciente.");

        hmMsg.put(6516,
                "ATENÇÃO: A data de termino de vigência da\n"
                + "prescrição anterior invade a data de início calculada \n"
                + "para a próxima prescrição desse paciente. Nesse caso \n"
                + "você deverá ajustar as datas de vigência da próxima\n"
                + "prescrição para evitar substituições não intencionais.");

        hmMsg.put(6604,
                "Não é permitido registrar evoluções em ocorrências baixadas.\n"
                + "(Vide regras complementares de baixa da ocorrência)");
        
        hmMsg.put(6605,
                "Não é permitido baixar ocorrências que não possuem registro de evoluções ou registro de resposta\n" 
                + "(Vide regras complementares de baixa da ocorrência)");
        
        hmMsg.put(6624,
                "Restrição de itens não padronizados está ativa.\n"
                + "O item selecionado não pode ser prescrito.");

        hmMsg.put(6625,
                "O item selecionado possui custo de referência igual R$ %s1. \n"
                + "Esse valor está acima do valor limite para o tipo %s2 que é de R$%s3.");

        hmMsg.put(6639,
                "Atenção, a remoção fará com que as alterações não salvas sejam perdidas.\n"
                + "Se existirem itens nessa condição, cancele a exclusão e execute uma SALVA,\n"
                + "antes de excluir itens.\n"
                //                + "\n"
                + "Deseja continuar ?");

        hmMsg.put(6642,
                "Existem itens na prescrição que não foram\n"
                + "marcados para renovação da prescrição.\n"
                + "Marque esses itens ou remova-os da tela\n"
                + "para que a prescrição possa ser salva.");

        hmMsg.put(6354,
                "Não é permitido salvar linhas sem especificar Código de Medicamento e Nome Comercial");

        hmMsg.put(6667,
                "Os seguintes itens contém medicamentos não padronizados,\n"
                + "é necessário informar uma justificativa para cada um dos itens:\n\n"
                + "%s1");

        hmMsg.put(6688, "Processo de assinatura abortado. Somente o profissional responsável pela prescrição  pode assiná-la. Caso você seja o profissional responsável pela prescrição, repesquise-a e tente novamente.");

        hmMsg.put(6697,
                "Acesso concorrente negado, existe uma outra estação\n"
                + "de trabalho dispensando ou executando edição\n"
                + "sobre essa prescrição \n"
                + "(Profissional, %s1).");

        hmMsg.put(6699,
                "A prescrição selecionada não está mais disponível para edições. \n"
                //                + "\n"
                + "Variáveis de Status da Prescrição: \n"
                + "- Inicio da Separação dos medicamentos na farmácia: %s1\n"
                + "- Inicio da Dispensação dos medicamentos: %s2\n"
                + "- Bloqueio de edição sobre prescrições liberadas: %s3\n"
                + "- Prescrição liberada: %s4\n"
                //                + "\n"
                + "Se a prescrição ainda não teve a sua dispensação iniciada você poderá estornar \n"
                + "a liberação da prescrição e tentar editá-la novamente. \n"
                //                + "\n"
                + "Você poderá realizar uma edição complementar sobre essa prescrição. \n"
                + "Deseja realizar edição complementar ?");

        hmMsg.put(6700,
                "A prescrição selecionada não está disponível para receber edições. \n"
                + "\n"
                + "Variáveis de Status da Prescrição:\n"
                + "Inicio da Separação dos medicamentos na farmácia: %s1\n"
                + "Inicio da Dispensação dos medicamentos: %s2\n"
                + "Bloqueio de edição sobre prescrições liberadas: %s3 \n"
                + "Prescrição liberada: %s4\n"
                //                + "\n"
                + "Edições complementares são autorizadas somente em prescrições \n"
                + "com datas de vigência não expirada. \n"
                //                + "\n"
                + "Se a prescrição ainda não teve a sua dispensação iniciada você poderá \n"
                + "estornar a liberação da prescrição e tentar editá-la novamente. ");

        hmMsg.put(6723, "Foram localizados sais prescritos aos quais o paciente é alérgico. Você deverá ou excluir ou justificar  a prescrição dos mesmos:\n\n%s1");

        hmMsg.put(6725, "Foram identificados itens com dosagem acima da dosagem máxima do  medicamento. Você deverá ou ajustar a dosagem ou justificá-la:\n\n%s1");

        hmMsg.put(6806, "Edição de responsabilidade em prescrição complementar válida apenas para itens lançados no gride que ainda não foram salvos. \n"
                + "Tente remover o item e insirá-o novamente, lembrando de alterar a responsabilidade 'antes de clicar no botão Salvar'.");

        hmMsg.put(6807, "Em prescrições complementares apenas itens adicionados e alterados podem ser excluídos.");

        hmMsg.put(6808, "Não é possível editar responsabilidade em prescrições liberadas.");

        hmMsg.put(6829, "Sitio não pertence a está prescrição, selecione um sítio válido ou crie um novo.");
        
        hmMsg.put(6849,
                "O grupo de dispensação do paciente não permite\n"
                + "registrar prescrição médica. \n"
                + "Verifque o cadastro do paciente.");

        hmMsg.put(6855,
                "Problemas de consistência na prescrição complementar.\n"
                + "As alterações serão descartadas. \n"
                + "Refaça as alterações, se o erro persistir entre em \n"
                + "contato com o suporte técnico.");

        hmMsg.put(6858, "Para assinar a prescrição é necessária a liberação médica da prescrição.");

        hmMsg.put(6859,
                "A data de início de vigência deve estar entre\n"
                + "%s1 e %s2.");

        hmMsg.put(6868, "Foram identificados medicamentos com dosagem crítica sem justificativa:\n\n%s1");

        hmMsg.put(6878,
                "Tipo de controle do item incompátivel com \n"
                + "tipo de controle do material:\n"
                + "\n%s1");

        hmMsg.put(6888, "Não é possível salvar a prescrição porque existem itens com frequências inativas:\n\n%s1");

        hmMsg.put(6889, "Foram encontrados itens com frequências inativas:\n%s1\nDeseja continuar a salva mesmo assim ?\n");

        hmMsg.put(6892, "Edição complementar de prescrição não autorizada. \n"
                + "Essa prescrição ainda não passou por liberação de enfermagem. \n"
                + "Para editar essa prescrição estorne a liberação da Prescrição Médica e entre no modo de edição normal.");

        hmMsg.put(6901, "Não é possivel fazer a assinatura digital da versão selecionada. Não foi encontrado o arquivo PDF da versão.");
        
        hmMsg.put(6971, "Há perguntas obrigatórias não respondidas no Questionário Dinâmico: %s1");
    }

    private Message() {
    }

    public static String get(int message, String... params) {
        String msg = null;

        if (hmMsg.containsKey(message)) {
//            msg = "[" + message + "] " + hmMsg.get(message);
            msg = hmMsg.get(message);
        } else {
            msg = "Mensagem " + message + " não encontrada";
        }

        if (params != null && params.length > 0) {
            StringBuffer sb = new StringBuffer(msg);
            for (int x = 0; x < params.length; x++) {
                String key = "%s" + (x + 1);
                msg = StringUtil.replaceAll(msg, key, params[x]);
            }
        }
        return msg;
    }
    
    private static String replace(String msg, String...params) {
        if (params != null && params.length > 0) {
            StringBuffer sb = new StringBuffer(msg);
            for (int x = 0; x < params.length; x++) {
                String key = "%s" + (x + 1);
                msg = StringUtil.replaceAll(msg, key, params[x]);
            }
        }
        return msg;
    }

    private static Command[] getButtons(MessageOption option) {
        Command cmd1 = new Command(OK);
        Command cmd2 = null;

        if (option == MessageOption.OK_CANCEL) {
            cmd2 = new Command(CANCEL);
        } else if (option == MessageOption.YES_NO) {
            cmd1.setCommandName(YES);
            cmd2 = new Command(NO);
        }
        
        Command[] buttons = new Command[]{cmd1};
        if (cmd2 != null) {
            buttons = new Command[]{cmd1, cmd2};
        }
        return buttons;
    }
    
    private static String getTitle(MessageType type) {
        String title = INFORMATION;

        if (type == MessageType.CONFIRMATION) {
            title = CONFIRMATION;
        } else if (type == MessageType.ERROR) {
            title = ERROR;
        } else if (type == MessageType.ATTENTION) {
            title = ATTENTION;
        }
        return title;
    }
    
    private static int getDialogType(MessageType type) {
        int dialogType = Dialog.TYPE_INFO;

        if (type == MessageType.CONFIRMATION) {
            dialogType = Dialog.TYPE_CONFIRMATION;
        } else if (type == MessageType.ERROR) {
            dialogType = Dialog.TYPE_ERROR;
        } else if (type == MessageType.ATTENTION) {
            dialogType = Dialog.TYPE_WARNING;
        }
        return dialogType;
    }
    
    private static TextArea getTextArea(String message) {
        TextArea text = Utilities.getTextAreaMedium(false, message, IwColorUtil.BLUE, IwColorUtil.WHITE, false);
        text.getAllStyles().setBgTransparency(255);
        text.getAllStyles().setPadding(1, 1, 1, 1);
                
        text.getAllStyles().setBorder(Border.createLineBorder(1, IwColorUtil.LTGRAY));
        return text;
    }
    
    public static Command show(MessageType type, int messageID, Command...buttons) {
        return Dialog.show("["+messageID+"] " + getTitle(type), getTextArea(get(messageID)), buttons, getDialogType(type), null);
    }

    public static Command show(MessageType type, String message, Command...buttons) {        
        return Dialog.show(getTitle(type), getTextArea(message), buttons, getDialogType(type), null);
    }

    public static boolean show(MessageType type, MessageOption option, String message) { 
        return show(type, option, message, (String[]) null);
    }
    
    public static boolean show(MessageType type, MessageOption option, String message, String... params) {
        message = replace(message, params);
        Command[] buttons = getButtons(option);
        Command cmd = Dialog.show(getTitle(type), getTextArea(message), buttons, getDialogType(type), null);
        return cmd.equals(buttons[0]);
    }
    
    public static boolean show(MessageType type, MessageOption option, int messageID) {
        return show(type, option, messageID, (String[]) null);
    }

    public static boolean show(MessageType type, MessageOption option, int messageID, String... params) {
        Command[] buttons = getButtons(option);
        Command cmd = Dialog.show("["+messageID+"] " + getTitle(type), getTextArea(get(messageID, params)), buttons, getDialogType(type), null);
        return cmd.equals(buttons[0]);
    }

}
