A = [6,7,8,9,10,5,1,2,3,4]
searchValue = 8
flag = 0

def binarySearch(A, low, high):
	global flag
	mid = (low + high)/2
	if(low <= high):
		if(searchValue == A[mid]):
			print "Found!"
			print "Index in Sorted List",mid
			flag = 1
		elif(searchValue < A[mid]):
			binarySearch(A,low,mid-1)
		else:
			binarySearch(A,mid+1,high)
	else:
		print "Illey!"
B = list(A)
A.sort()
binarySearch(A,0,len(A))

if(flag):
	for i in range (len(B)):
		if(searchValue == B[i]):
			print "Index in Original List ",i
			break
		
