import threading
import time
import socket

sem = threading.Semaphore(5)
port = 12348


def philEat1():
    sem.acquire()
    sem.acquire()
    print "Philosopher 1 Eating"
    #time.sleep(10)
    clientSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientSock.connect(('localhost',port))
    clientSock.send("1")
    r = clientSock.recv(1024)
    if(r=="-1"):
        print "Error in database"
    #while(not clientSock.recv(1024)=="0"):
    #    pass
    clientSock.close()
    sem.release()
    sem.release()
    print "Philosopher 1 Thinking"

def philEat2():
    sem.acquire()
    sem.acquire()
    print "Philosopher 2 Eating"
    #time.sleep(1)
    clientSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientSock.connect(('localhost',port))
    clientSock.send("2")
    r = clientSock.recv(1024)
    if(r=="-1"):
        print "Error in database"
    #while(not clientSock.recv(1024)=="0"):
    #    pass
    clientSock.close()
    sem.release()
    sem.release()
    print "Philosopher 2 Thinking"

def philEat3():
    sem.acquire()
    sem.acquire()
    print "Philosopher 3 Eating"
    #time.sleep(6)
    clientSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientSock.connect(('localhost',port))
    clientSock.send("3")
    r = clientSock.recv(1024)
    if(r=="-1"):
        print "Error in database"
    #while(not clientSock.recv(1024)=="0"):
    #    pass
    clientSock.close()
    sem.release()
    sem.release()
    print "Philosopher 3 Thinking"

def philEat4():
    sem.acquire()
    sem.acquire()
    print "Philosopher 4 Eating"
    #time.sleep(12)
    clientSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientSock.connect(('localhost',port))
    clientSock.send("4")
    r = clientSock.recv(1024)
    if(r=="-1"):
        print "Error in database"
    #while(not clientSock.recv(1024)=="0"):
    #    pass
    clientSock.close()
    sem.release()
    sem.release()
    print "Philosopher 4 Thinking"

def philEat5():
    sem.acquire()
    sem.acquire()
    print "Philosopher 5 Eating"
    #time.sleep(3)
    clientSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientSock.connect(('localhost',port))
    clientSock.send("5")
    r = clientSock.recv(1024)
    if(r=="-1"):
        print "Error in database"
    #while(not clientSock.recv(1024)=="0"):
    #    pass
    clientSock.close()
    sem.release()
    sem.release()
    print "Philosopher 5 Thinking"
    
t1 = threading.Thread(target=philEat1)
print "Philosopher 1 wants to eat"
t1.start()

time.sleep(1)
t2 = threading.Thread(target=philEat2)
print "Philosopher 2 wants to eat"
t2.start()

time.sleep(1)
t3 = threading.Thread(target=philEat3)
print "Philosopher 3 wants to eat"
t3.start()

time.sleep(6)
t4 = threading.Thread(target=philEat4)
print "Philosopher 4 wants to eat"
t4.start()

time.sleep(1)
t5 = threading.Thread(target=philEat5)
print "Philosopher 5 wants to eat"
t5.start()

t1.join()
t2.join()
t3.join()
t4.join()
t5.join()

print "All done"
