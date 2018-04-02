from bottle import route, run, template, post, request

AQ = []
M = []

@route('/hello/<name>')
def index(name):
    return template('<marquee><h2>Hello {{name}}!</h2></marquee>', name=name)
    #return template('index.html')

@route('/')
def index():
    return template('index.html')

@post('/')
def readNums():
    global AQ, M
    postdata= request.body.read()
    print postdata
    m = request.forms.get("m")
    q = request.forms.get("q")

    A = [0,0,0,0]
    AQ = []
    M = []
    Q = []
    
    #m = 6
    binM = "{0:04b}".format(int(m))
    
    for i in binM:
            M.append(int(i))
    #q = 2
    binQ = "{0:04b}".format(int(q))
    
    for i in binQ:
            Q.append(int(i))
    Q.append(0)
    
    AQ = A + Q

    AQdash = list(AQ)

    for i in range(len(M)):
            if(AQ[7]==AQ[8]):
                    #print "shift"
                    shift()
                    #print AQ
            elif(AQ[7:]==[0,1]):
                    #print "add"
                    add()
                    #print AQ
                    shift()
                    #print "shift"
                    #print AQ
            else:
                    #print "sub"
                    sub()
                    #print AQ
                    shift()
                    #print "shift"
                    #print AQ
                    
    binary = ''.join(str(e) for e in AQ)
    binary = binary[:len(binary)-1]

    #print "Result:"
    #print "In binary",binary
    #print "In decimal",int(binary,2)

    return "Result:<br> In Decimal: {0} <br> In Binary: {1}".format(int(binary,2), binary)
    #return "Multiplicand(M) : {0} Multiplier(Q): {1}".format(binM, binQ)
    #return "A : {0} <br>M: {1} <br>Q : {2} <br>AQ : {3} <br>AQdash : {4}".format(A, M, Q, AQ, AQdash)

def shift():
    global AQ

    AQ = AQ[:len(AQ)-1]
    AQ.insert(0,AQ[0])

def add():
    global AQ
    c=0
    for i in range(3,-1,-1):      
        AQ[i] = AQ[i] + M[i] + c
        if AQ[i]==2:
            AQ[i] = 0
            c=1
        else:
            c=0
def sub():
    global AQ

    b=0
    for i in range(3,-1,-1):
        if(b):
            AQ[i] = int(not AQ[i])
            if(not AQ[i]):
                b = 0
        AQ[i] = AQ[i] - M[i]
        if AQ[i]==-1:
            AQ[i] = 1
            b=1
                            
run(host='localhost', port=8080)
