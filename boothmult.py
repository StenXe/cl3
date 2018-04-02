#M = [0,1,1,0]
#Q = [0,0,1,0,0]
A = [0]*8

flag = False

m = -16

if(m<0):
        m *= -1
        binM = "{0:08b}".format(m)
        flag = ~flag
else:
        binM = "{0:08b}".format(m)
M = []

for i in binM:
        M.append(int(i))
        
q = -2

if(q<0):
        q *= -1
        binQ = "{0:08b}".format(q)
        flag = ~flag
else:
        binQ = "{0:08b}".format(q)
Q = []
for i in binQ:
        Q.append(int(i))
Q.append(0)
AQ = []

AQ = A + Q

print AQ

def negativeNumToBin(negativeNum):
        oneBit = [0]*(len(negativeNum)-1)
        oneBit.append(1)
        c = 0
        for i in range(len(negativeNum)):
                if(negativeNum[i]):
                        negativeNum[i] = 0
                else:
                        negativeNum[i] = 1
        for i in range(len(negativeNum)-1,-1,-1):
                negativeNum[i] = negativeNum[i] + oneBit[i] + c
                if negativeNum[i]==2:
                        negativeNum[i] = 0
                        c = 1
                else:
                        c = 0

        return negativeNum
def shift():
        global AQ
        
        AQ = AQ[:len(AQ)-1]
        AQ.insert(0,AQ[0])
        

def add():
        global AQ
        c=0
        for i in range(7,-1,-1):      
                AQ[i] = AQ[i] + M[i] + c
                if AQ[i]==2:
                        AQ[i] = 0
                        c=1
                else:
                        c=0
def sub():
        global AQ

        b=0
        for i in range(7,-1,-1):
                if(b):
                        AQ[i] = int(not AQ[i])
                        if(not AQ[i]):
                                b = 0
                AQ[i] = AQ[i] - M[i]
                if AQ[i]==-1:
                        AQ[i] = 1
                        b=1      
if(flag):
        M = negativeNumToBin(M)

for i in range(len(M)):
        
        if(AQ[15]==AQ[16]):
                print "shift"
                shift()
                print AQ
        elif(AQ[15:]==[0,1]):
                print "add"
                add()
                print AQ
                shift()
                print "shift"
                print AQ
        else:
                print "sub"
                sub()
                print AQ
                shift()
                print "shift"
                print AQ
                
if(flag):
        binaryDisp = ''.join(str(e) for e in AQ[:16])
        AQ = negativeNumToBin(AQ[:16])
        binary = ''.join(str(e) for e in AQ)
else:
        binary = ''.join(str(e) for e in AQ)
        binary = binary[:len(binary)-1]
        binaryDisp = binary
        
print "Result:"
print "In binary",binaryDisp
if(flag):
        print "In decimal -",int(binary,2)
else:
        print "In decimal ",int(binary,2)

'''
dec = 0
for i in range (len(AQ)):
        if(AQ[len(AQ)-i-2]):
                dec += 2**i

print "In decimal",dec
'''


