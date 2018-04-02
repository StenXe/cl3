#include<iostream>
#include <string.h>
#include<time.h>
#include <fstream>
#include <vector>
#include"rapidxml.hpp"
#define SIZE 10

using namespace rapidxml;
using namespace std;
void quicksort(int a[],int low,int high);
int partition(int a[],int low,int high);
int main()
{

	int a[SIZE];	

	xml_document<> doc;
	xml_node<> * root_node;

	ifstream theFile ("arrayXML.xml");
	vector<char> buffer((istreambuf_iterator<char>(theFile)), istreambuf_iterator<char>());
	buffer.push_back('\0');

	doc.parse<0>(&buffer[0]);

	root_node = doc.first_node("Array");

	int i = 0;

	cout<<"Numbers parsed from XML:"<<endl;

	for (xml_node<> * brewery_node = root_node->first_node("Number"); brewery_node; brewery_node = brewery_node->next_sibling())
	{
	    printf("%d", 
	    	atoi(brewery_node->first_attribute("num")->value()));

		a[i] = atoi(brewery_node->first_attribute("num")->value());
	
		i++;
	    	
	    cout << endl;
	}

	clock_t t;

	t = clock();

	/*for(int i=0;i<SIZE;i++)
	{
		a[i] = SIZE - i;
	}*/

	quicksort(a,0,SIZE-1);
	
	cout<<"Sorted Numbers:"<<endl;

	for(int i=0;i<SIZE;i++)
		cout<<a[i]<<endl;

	t = clock() - t;

	cout<<"Total time "<<((float)t)/CLOCKS_PER_SEC<<endl;
	return 0;
}
void quicksort(int a[],int low,int high)
{
	if(low<high)
	#pragma omp parallel
	{
	{
		int p = partition(a,low,high);

		quicksort(a,low,p-1);
		quicksort(a,p+1,high);
	}
	}
}

int partition(int a[],int low,int high)
{
	int pivot = a[high];

	int i = low - 1;

	for(int j=low;j<high;j++)
	{
		if(a[j]<pivot)
		{
			i++;
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}
	int temp = a[i+1];
	a[i+1] = a[high];
	a[high] = temp;

	return (i+1);
}
