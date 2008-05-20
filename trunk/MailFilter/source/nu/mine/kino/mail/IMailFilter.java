/*******************************************************************************
 * Copyright (c) 2008 Masatomi KINO. All rights reserved. $Id$
 ******************************************************************************/
// �쐬��: 2008/05/19
package nu.mine.kino.mail;

/**
 * ���[�����t�B���^����C���^�t�F�[�X�ł��B
 * 
 * @author Masatomi KINO
 * @version $Revision$
 */
public interface IMailFilter {
    /**
     * �����Ƀ��[���f�[�^���n���Ă���̂ŁA���e�����ăt�B���^�[���Ă��������B ��{�I�ɂ́A
     * <ul>
     * <li>�n�j�Ȃ�A���̂܂܂�������f�[�^��߂�</li>
     * <li>�m�f�ŏ�����i�߂��������Ȃ��Ȃ�A��O���X���[����</li>
     * </ul>
     * �Ƃ���΂悢�Ǝv���܂��B�ꍇ�ɂ���Ă͓��e��ҏW���Ė߂��Ă��悢�Ǝv���܂��B
     * 
     * @param mailData
     * @return ���̃t�B���^�ɓn�����߂̃��[���f�[�^
     * @throws FilterException
     */
    public String doFilter(String mailData) throws FilterException;
}
