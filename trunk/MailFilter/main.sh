#!/bin/bash

# Java�Υ��󥹥ȡ���ǥ��쥯�ȥ�����
export JAVA_HOME=/opt/jdk1.5.0_12
export HOME_DIR=`echo $HOME`

# MailFilter���֤����ǥ��쥯�ȥ���֤������Ƥ�������
export LIBDIR=${HOME_DIR}/MailFilter/lib

# ${LIBDIR}�ǥ��쥯�ȥ꼫�Τˤ⡢�ѥ����̤�
export CLASSPATH=${LIBDIR}
# ���饹�ѥ������� ${LIBDIR} ���jar���٤��̤�
jarList=`ls ${LIBDIR} | grep .jar`
for jarFile in ${jarList}
do
  # echo  ${LIBDIR}/${jarFile}
  export CLASSPATH=${CLASSPATH}:${LIBDIR}/${jarFile}
done

export PATH=${JAVA_HOME}/bin:${PATH}
# ���饹�ѥ������� �ʾ�


# ɸ�����Ϥ����ͤ����
(
tmp="";

# ���ڤ�ʸ�����ѹ� tab��̵�뤵���Τ���򤷤Ƥ���
IFS=$'\n';
while read line
do
 tmp="${tmp}${line}\n";
done

# nu.mine.kino.mail.impl.Main ���饹�����ｪλ������
# post2blog.pl ��ƤӽФ�
# �����ʤ�¹Ԥ��ʤ��褦��stub�����ꤵ��Ƥ���Τǽ񤭴����Ʋ�����
if
  echo -en "$tmp" | java  nu.mine.kino.mail.impl.Main
then
#  echo -en "$tmp" | ${HOME_DIR}/post2blog.pl
  echo -en "$tmp" | ${HOME_DIR}/MailFilter/stub.sh
fi
)

exit 0