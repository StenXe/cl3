from bottle import route, run, template, post, request
from collections import Counter
import math

@route('/')
def index():
    return template('indexPlagChecker.html')

@post('/')
def plagChecker():

    postdata = request.body.read()
    Doc = open("doc.txt","r")
    #Doc = "This is big classroom"
    print postdata
    #query = "bIg House"
    query = request.forms.get("txtbox")
    #query = postdata.split("=")[1]

    query = query.replace("+"," ")
    query = query.replace("\r\n"," ")
    query = query.lower()
    print query
    queryS = query.split(" ")
    print queryS
    DocLines = Doc.readlines()

    DocLines = ''.join(DocLines)
    
    DocLines = DocLines.lower()

    DocS = DocLines.split(" ")

    termFreqDoc = dict(Counter(DocS))

    termFreqQ = dict(Counter(queryS))

    f1 = {}
    f2 = {}
    for word in termFreqDoc:
        if word in termFreqQ:
            f1[word] = termFreqDoc[word]*termFreqQ[word]
        else:
            f1[word] = 0

    #print "F1:",f1

    for word in termFreqQ:
        if word in termFreqDoc:
            f2[word] = termFreqDoc[word]*termFreqQ[word]
        else:
            f2[word] = 0

    #print "F2;",f2

    f1.update(f2)

    numerator = 0
    for i in f1:
        numerator += f1[i]

    #print "Numerator",numerator

    denominator = 0
    dSum = 0
    for i in termFreqDoc:
        dSum += termFreqDoc[i]*termFreqDoc[i]
    sumSqrtDoc = math.sqrt(dSum)

    dSum = 0
    for i in termFreqQ:
        dSum += termFreqQ[i]*termFreqQ[i]
    sumSqrtQ = math.sqrt(dSum)

    denominator = sumSqrtDoc*sumSqrtQ

    #print "Cosine Similarity",(numerator/denominator)
    return "This document is ",str(round(numerator/denominator,4)*100),"% plagiarised!"
run(host='localhost',posrt=8080, debug=True)
