#!/bin/bash

# ɸ�����Ϥ����ͤ����
(
tmp="";

echo "start. ";

# ���ڤ�ʸ�����ѹ� tab��̵�뤵���Τ���򤷤Ƥ���
IFS=$'\n';
while read line
do
 echo "${line}";
done

echo "end. ";
)
