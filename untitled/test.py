import MySQLdb
import json

db = MySQLdb.connect("localhost", "root", "1234", "smartHomework")
cursor = db.cursor()
cursor.execute("SELECT * FROM `smartHomework`.`teacher` LIMIT 1000;")
data = cursor.fetchall()

_json = '{"Student_answer":[{"Studentfk": "S0001", "Questionfk": "2", "Answerfk": "2"},{"Studentfk": "S0001", "Questionfk": "3", "Answerfk": "3"}]}'
data = json.loads(_json)

count = 0;
for index in data['Student_answer']:
    print data['Student_answer'][count]['Studentfk']
    print data['Student_answer'][count]['Questionfk']
    print data['Student_answer'][count]['Answerfk']
    count+=1

print (data)
db.close()
