
import pandas as pd
import numpy as np

#columns = np.array(['fecha','hora'])#, 'type':[], 'clase':[], 'thread':[], 'msg':[]}
fecha=[]
hora=[]
iden=[]
tipo=[]
clase=[]
thread=[]
msg=[]


f = open("../backend/target/logs/app.log","r")

for line in f :
    arr = line.split(" ")
    if len(arr)>6 :
        try:
            fecha.append(pd.Timestamp(arr[0]+ " "+arr[1]))
            iden.append(arr[2])
            tipo.append(arr[3])
            clase.append(arr[4])
            thread.append(arr[5])
            msg.append(" ".join(arr[6:]))
        except:
            print('no es una fecha {}'.format(arr[0]+" "+arr[1]))
df = pd.DataFrame({'fecha':fecha, 'iden':iden, 'tipo':tipo, 'clase':clase, 'thread':thread,'msg':msg})
f.close()

daoAspect=df[df['clase']=='c.l.d.a.daoAspect']
daoAspect['fecha'].hist(bins=10)