#!/bin/bash

export JAVA_HOME=/opt/jdk1.5.0_12
export HOME_DIR=`echo $HOME`

# MailFilter���֤����ǥ��쥯�ȥ���֤�������
export LIBDIR=${HOME_DIR}/MailFilter/lib

# ���饹�ѥ������� ${LIBDIR} ���jar���٤��̤�
jarList=`ls ${LIBDIR} | grep .jar`
export CLASSPATH=${LIBDIR}
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

if 
  echo -en "$tmp" | java  nu.mine.kino.mail.impl.Main
then
#  echo -en "$tmp" | ${HOME_DIR}/post2blog.pl
  echo -en "$tmp" | ${HOME_DIR}/MailFilter/stub.sh
fi
)

#-----------------------
#java  nu.mine.kino.mail.impl.Main

exit 0