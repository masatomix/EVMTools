#!/bin/bash

export JAVA_HOME=/opt/jdk1.5.0_12
# MailFilter���֤����ǥ��쥯�ȥ���֤�������
export LIBDIR=/home/hogehoge/MailFilter/lib

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



# ������ä�ɸ����Ϥ���ǡ�����������ȡ����֤��֤�����äƤ��ޤ����ᡢ�Ȥꤢ���������ȥ����ȡ�
#-----------------------
# ɸ�����Ϥ����ͤ����
#tmp="";

#while read line
#do
# tmp="${tmp}${line}\n";
#done


#echo -e "$tmp" |java  nu.mine.kino.mail.impl.Main 
#-----------------------
java  nu.mine.kino.mail.impl.Main

#exit 0