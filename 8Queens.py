import numpy as np
import xml.etree.ElementTree as xlP
import Tkinter as tk

backtrack = False

#queenNum is row of queen to be placed
#loc is column of queen to be placed
def placeQueen(queenMatrix, queenNum, availLoc):
    global backtrack
    if(len(availLoc)>0):
        
        i = 0
        while(i < len(availLoc)):
            #print "I",i
            loc = availLoc.pop(i)
            #print "loc",loc
            #print "Avail Locs ",availLoc
            flag = 1
            #print "Queen",queenNum
            for row in range(queenNum):
                col = np.where(queenMatrix[row] == 'Q')[0]
                #print queenMatrix
                #print "col",col
                #print "To place ",queenNum,loc
                #print "comp ",row,col
                if((queenNum - row) == 0 or (loc - col) == 0):
                    #print "Same row col"
                    availLoc.insert(i,loc)
                    flag = 0
                    i += 1
                    break
                elif(abs(queenNum - row) == abs(loc - col)):
                    #print "Same Diag"
                    availLoc.insert(i,loc)
                    flag = 0
                    i += 1
                    break
                else:
                    pass
            if(flag):
                #print "Place at ",queenNum,loc
                queenMatrix[queenNum][loc] = 'Q'
                placeQueen(queenMatrix, queenNum+1, availLoc)
                if(backtrack):
                    #print "backtack",queenNum
                    queenMatrix[queenNum][loc] = '-'
                    availLoc.insert(i,loc)
                    i += 1
                    backtrack = False
                    if(i >= len(availLoc)):
                        backtrack = True
            else:
                backtrack = True
                


    #for i in range(queenNum):

r,c = 8,8
availLoc = [x for x in range(r)]
initAvailRowLoc = []
queenMatrix = np.array([["-" for x in range(r)] for y in range(c)])

e = xlP.parse('arrayInput.xml')
root = e.getroot()

for num in root.findall('num'):
    row = num.get('row')
    col = num.get('col')
    val = num.find('digit').text

    if(val == "Q"):
        initAvailRowLoc.append(int(row))
        availLoc.remove(int(col))
    queenMatrix[int(row)][int(col)] = val

#queenMatrix = np.array([[0 for x in range(r)] for y in range(c)])

#queenMatrix[0][2] = 1
#queenMatrix[1][5] = 1

print queenMatrix

placeQueen(queenMatrix, 1, availLoc)

print queenMatrix

top = tk.Tk()

C = tk.Canvas(top, bg="white", height=400, width=400)

vertLine1 = C.create_line(50,0,50,400)
vertLine1 = C.create_line(100,0,100,400)
vertLine1 = C.create_line(150,0,150,400)
vertLine1 = C.create_line(200,0,200,400)
vertLine1 = C.create_line(250,0,250,400)
vertLine1 = C.create_line(300,0,300,400)
vertLine1 = C.create_line(350,0,350,400)

horizLine1 = C.create_line(0,50,400,50)
horizLine1 = C.create_line(0,100,400,100)
horizLine1 = C.create_line(0,150,400,150)
horizLine2 = C.create_line(0,200,400,200)
horizLine1 = C.create_line(0,250,400,250)
horizLine1 = C.create_line(0,300,400,300)
horizLine1 = C.create_line(0,350,400,350)

for row in range(8):
    col = np.where(queenMatrix[row] == 'Q')[0]
    #print "row",row
    #print "col",col
    if(row in initAvailRowLoc):
        C.create_text(int(col*50+25),int(row*50+25),font="default 25 bold",text="Q",fill="red")
    else:
        C.create_text(int(col*50+25),int(row*50+25),font="default 25 bold",text="Q",activefill="green")
C.pack()
top.title("8 Queens Solution")
top.resizable(width=False, height=False)
top.mainloop()
