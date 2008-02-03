package nu.mine.kino.strutsexamples.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @version 1.0
 * @author
 */
public class ErrorAction extends Action {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ErrorAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // http://www.javaroad.jp/opensource/js_struts17.htm
        // http://civic.xrea.jp/2006/09/04/struts-message-2/
        ActionForward forward = new ActionForward(); // return value
        ActionMessages messages = new ActionMessages();

        // �܂��́Aproperties����łȂ��A�R�R�Ŏw�肵������������̂܂܉�ʕ\������p�^���B
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("���b�Z�[�W�O",
                false));// ���\�[�X�t���O��false�ɂ���B �w�肵�Ȃ��ꍇ�f�t�H���g��true

        // ����properties���當������擾����p�^���B"msg.message1"�Ƃ����L�[�l��properties���������A
        // ��ʕ\���B"���b�Z�[�W�P"�͉ϕ�����ŁAproperties����
        // msg.message1={0} <-�R�R���u�������B
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "msg.message1", "���b�Z�[�W�P"));

        // �σp�����^�͔z����n�j�B���̂΂���
        // msg.message2= {0},{1} �Ȃǂƕ����v���[�X�z���_��������B
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "msg.message2", new String[] { "���b�Z�[�W�Q�̂P", "���b�Z�[�W�Q�̂Q" }));

        // ActionMessages.GLOBAL_MESSAGE�Ƃ����̂̓O���[�v��ID�ɂȂ��Ă��āA�Ǝ���ID��n�����Ƃ��ł���B
        // Message��\������JSP���ŁA�^�O�̎w��ɃO���[�vID���w�肷��΁A���̃`����ID�̃��b�Z�[�W��\���\�B
        // �ڍׂ�JSP���ŁB
        messages.add("HogeGroup", new ActionMessage("�ʃO���[�v�̃��b�Z�[�W", false));

        // saveMessage���邱�ƂŁA
        // request.setAttribute(Globals.ERROR_KEY, errors);
        // �Ƃ����������s����B
        saveErrors(request, messages);
        return mapping.findForward("success");

    }
}
