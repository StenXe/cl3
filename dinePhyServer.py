import socket
import time
#import mysql.connector
import pymongo
from pymongo import MongoClient
from thread import *

def connectDB(clientSock,msg):
    try:
        '''
        cnx = mysql.connector.connect(user='root',password='xyz123',host='127.0.0.1',database='testdb')
        cursor = cnx.cursor()

        #query = "SELECT * FROM emp;"
        qid = 104+int(msg) 
        query = 'INSERT INTO emp values('+str(qid)+',"qwe","rew");'
        print "query",query
        cursor.execute(query)

        clientSock.send("0")

        cnx.close()
        #for(id, name, address) in cursor:
        #    print id, name, address
        '''

        client = MongoClient()

        db = client.testdb

        collection = db.testcollection

        eid = 112+int(msg)
        post = {"eid":eid,"name":"pqr","address":"chan"}

        result = collection.insert_one(post)

        clientSock.send("0")

        client.close()
    except:
        print "Error"
        clientSock.send("-1")
        if(not client == None):
            client.close()

try:
    
    serverSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    serverSock.bind(('localhost',12348))

    serverSock.listen(5)

    print "Server Running"
    while True:
        try:
            (clientSock, address) = serverSock.accept()

            print "Connection from",address

            msg = clientSock.recv(1024)

            print "Philosopher",msg,"connected"

            start_new_thread(connectDB,(clientSock,msg))
            #time.sleep(2)

            #clientSock.send("0")

            
        except KeyboardInterrupt:
            serverSock.close()
            print "Close"
except KeyboardInterrupt:
    serverSock.close()
    print "Server Stopped!"
    
