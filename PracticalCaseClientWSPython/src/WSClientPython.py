'''
Created on 23/03/2012

@author: Vicenc Font
'''

if __name__ == '__main__':
    pass
# import the libraries
import json
import sys
from xml.etree import ElementTree, ElementPath
import restful_lib

def getJson(url):
    conn = restful_lib.Connection(url, username="", password="")
    resp = conn.request_get("", args={}, headers={'content-type':'application/json', 'accept':'application/json'})
    status = resp[u'headers']['status']
    # check that we either got a successful response (200) or a previously retrieved, but still valid response (304)
    if status == '200' or status == '304':
        result2 = json.loads(resp[u'body'])
    else:
        print 'Error status code: ', status
    return result2

def getXml(url):
    conn = restful_lib.Connection(url, username="", password="")
    resp = conn.request_get("", args={}, headers={'content-type':'application/xml', 'accept':'application/xml'})
    status = resp[u'headers']['status']
    # check that we either got a successful response (200) or a previously retrieved, but still valid response (304)
    if status == '200' or status == '304':
        tree = ElementTree.XML(resp[u'body'])
    else:
        print 'Error status code: ', status
    return tree

try:  
    result = getXml("http://localhost:8080/PracticalCaseWS/WSCatalogRest/listAllCategories");
    exitall = 1
    while exitall>0:
        n=1  
        menu =[]      
        print("");
        print("          P E T S    S H O P");
        print("          Practical Case Study based on Rest Web Service and Python Client");
        print("          You can list all the pets or list them by category.");
        print("");
        for elem in ElementPath.findall(result, ".//category"):
            print "          ",n," - ",elem.find('name').text , " "
            menu.append(elem.find('name').text)
            n +=1
        print("           0 - Exit "); 
        c1 = raw_input("           Choose a number: ")                                            
        try: 
            c = int(c1)
            if c==0:
                exitall = 0
            else:
                if c>0 and c<n:
                    pet = []
                    if menu[c-1].rstrip() =="ALL PETS":
                        doc = getXml("http://localhost:8080/PracticalCaseWS/WSCatalogRest/listAllPets");                                                
                    else:
                        doc = getXml("http://localhost:8080/PracticalCaseWS/WSCatalogRest/listPetsByCategory/"+menu[c-1])                                                
                    print("")
                    print("          Number Pet's Name    Description       Price   Category    ")                       
                    np = 1
                    for elem1 in ElementPath.findall(doc, ".//pet"):
                        print "          ",str(np).rjust(2)," -",elem1.find('name').text.ljust(12)," "+elem1.find('description').text.ljust(16),elem1.find('price').text.rjust(6)," ",elem1.find('category').text;
                        pet.append(elem1.find('id').text)   
                        np +=1                                                       
                    print("")
                    c1 = raw_input("          Puts Pet's number or '0' to exit: ")                                            
                    c2 = int(c1)
                    print("")
                    print("")  
                    if c2>0 and c2<=len(pet):
                        datapet = getJson("http://localhost:8080/PracticalCaseWS/WSCatalogRest/showPet/"+pet[c2-1])
                        print("          I N F O R M A T I O N   A B O U T   P E T")                          
                        print("")
                        print "          ID number      : ", datapet["id"] , " "                        
                        print("          Data From      : "+ datapet["datebirth"] + " ")                               
                        print("          Pet name       : "+ datapet["name"] + " ")
                        print("          Pet description: "+ datapet["description"] + " ")
                        print "          Price          : ", datapet["price"] , " "
                        print("          Class pet      : "+ datapet["category"] + " ")
                        print("")
                        c3 = raw_input("          Press ENTER to continue: ")                                                                                    
        except:
            print "Unexpected error:", sys.exc_info()[0] 
except :
    print "Unexpected error:", sys.exc_info()[0]