from os import environ
from flask import Flask
from flask_restful import Resource, Api
from flask_restful import reqparse
from flaskext.mysql import MySQL
import json

import random

mysql = MySQL()
app = Flask(__name__)

# MySQL configurations
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = '1234'
app.config['MYSQL_DATABASE_DB'] = 'smartHomework'
app.config['MYSQL_DATABASE_HOST'] = 'localhost'
# app.config['MYSQL_DATABASE_HOST'] = '27.254.63.25'

mysql.init_app(app)

api = Api(app)


@app.route('/')
def hello_world():
    return 'Hello, World!'


class AuthenticateUser(Resource):
    def post(self):
        try:
            # Parse the arguments

            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address for Authentication')
            parser.add_argument('password', type=str, help='Password for Authentication')
            args = parser.parse_args()

            _userEmail = args['email']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('sp_AuthenticateUser', (_userEmail,))
            data = cursor.fetchall()

            if (len(data) > 0):
                if (str(data[0][2]) == _userPassword):
                    return {'status': 200, 'UserId': str(data[0][0])}
                else:
                    return {'status': 100, 'message': 'Authentication failure'}

        except Exception as e:
            return {'error': str(e)}


class UpdateQuestion(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('UserID', type=str)
            parser.add_argument('position', type=str)
            args = parser.parse_args()

            _userId = args['UserID']
            _position = args['position']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM `smartHomework`.`Homeworkset` WHERE `Studentfk` = '%s'" % (_userId))
            data = cursor.fetchall()

            items_list = [];
            for item in data:
                i = {
                    'HsetID': item[0],
                    'ExerID': item[1],
                    'Score': item[2],
                    'Studentfk': item[3]
                }
                items_list.append(i)

            return {'StatusCode': '200', 'items_list': items_list}

        except Exception as e:
            return {'error': str(e)}


class random(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('StudentID', type=str)

            # parser.add_argument( 'AmountDo', type=int )
            # parser.add_argument( 'position', type=str )
            args = parser.parse_args()

            _StudentID = args['StudentID']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(
                "SELECT QuestionID FROM Exercise e INNER JOIN Question q on e.ExerID = q.Exercisefk ORDER BY RAND() limit 5 ;")

            data = cursor.fetchall()

            items_list = [];
            for item in data:
                cursor.executemany("INSERT INTO Random(Questionfk)VALUES ('%s')", (tuple(item),))
                conn.commit()
                items_list.append([item])

            return {'StatusCode': '200', 'Question': items_list}

        except Exception as e:
            return {'error': str(e)}


class UpdateAnswerOfStudent(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('Student', type=str)
            parser.add_argument('Question', type=str)
            parser.add_argument('AnswerChoice', type=str)
            args = parser.parse_args()

            _studentId = args['Student']
            _questionId = args['Question']
            _choiceAnswer = args['AnswerChoice']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM Student_answer WHERE `Studentfk` = '%s' " % (_studentId))
            data = cursor.fetchall()

            items_list = [];
            for item in data:
                i = {
                    'HisId': item[0],
                    'Studentfk': item[1],
                    'Qusetionfk': item[2],
                    'Answerfk': item[3],

                }
                items_list.append(i)

            return {'StatusCode': '200', 'StudentAnswer': items_list}

        except Exception as e:
            return {'error': str(e)}


class GetAlIQuestion(Resource):
    def post(self):
        try:
            # Parse the arguments
            # parser = reqparse.RequestParser()
            # parser.add_argument('TeacherID', type=str)
            # args = parser.parse_args()
            #
            # _userId = args['TeacherID']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM `Question` LIMIT 1000;")
            data = cursor.fetchall()

            items_list = [];
            for item in data:
                i = {
                    'QuestionID': item[0],
                    'Proposition': item[1],
                    'Exercisefk': item[2],
                    'Correct': item[3],
                    'AnsDesc': item[4]
                }
                items_list.append(i)

            return {'StatusCode': '200', 'Question': items_list}

        except Exception as e:
            return {'error': str(e)}


class Login(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('username', type=str)
            parser.add_argument('password', type=str)
            args = parser.parse_args()

            _userId = args['username']
            _password = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM `Teacher` WHERE `T_Password` = '%s' AND `T_Username` = '%s'" % \
                           (_password, _userId))

            if (cursor.rowcount > 0):
                data = cursor.fetchall()
                items_list = [];
                for item in data:
                    i = {
                        'userid': item[0],
                        'name': item[1],
                        'lastname': item[2],
                        'tel': item[3],
                        'username': item[4],
                        'position': 'Teacher'
                    }
                    items_list.append(i)

                return {'status': 'true', 'user': items_list}

            cursor.execute(
                "SELECT ParentID, P_Name, P_Lname, P_Tel, P_Username, StudentID FROM parent INNER JOIN student ON parent.ParentID = student.Parentfk WHERE `P_Password` = '%s' AND `P_Username` = '%s'" % \
                (_password, _userId))
            if (cursor.rowcount > 0):
                items_list = [];

                for (ParentID, P_Name, P_Lname, P_Tel, P_Username, StudentID) in cursor:
                    i = {
                        'userid': ParentID,
                        'name': P_Name,
                        'lastname': P_Lname,
                        'tel': P_Tel,
                        'username': P_Username,
                        'StudentID': StudentID,
                        'position': 'Parent'
                    }
                items_list.append(i)

                return {'status': 'true', 'user': items_list}

            cursor.execute("SELECT * FROM `Student` WHERE `S_Password` = '%s' AND `S_Username` = '%s'" % \
                           (_password, _userId))
            if (cursor.rowcount > 0):
                data = cursor.fetchall()
                items_list = [];
                for item in data:
                    i = {
                        'userid': item[0],
                        'name': item[1],
                        'lastname': item[2],
                        'tel': item[3],
                        'username': item[4],
                        'position': 'Student'
                    }
                    items_list.append(i)

                return {'status': 'true', 'user': items_list}

            return {'status': 'false'}

        except Exception as e:
            return {'status': str(e)}


class AddItem(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('id', type=str)
            parser.add_argument('item', type=str)
            args = parser.parse_args()

            _userId = args['id']
            _item = args['item']

            print _userId;

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('sp_AddItems', (_userId, _item))
            data = cursor.fetchall()

            conn.commit()
            return {'StatusCode': '200', 'Message': 'Success'}

        except Exception as e:
            return {'error': str(e)}


class CreateUser(Resource):
    def post(self):
        try:
            # Parse the arguments
            parser = reqparse.RequestParser()
            parser.add_argument('email', type=str, help='Email address to create user')
            parser.add_argument('password', type=str, help='Password to create user')
            args = parser.parse_args()

            _userEmail = args['email']
            _userPassword = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.callproc('spCreateUser', (_userEmail, _userPassword))
            data = cursor.fetchall()

            if len(data) is 0:
                conn.commit()
                return {'StatusCode': '200', 'Message': 'User creation success'}
            else:
                return {'StatusCode': '1000', 'Message': str(data[0])}

        except Exception as e:
            return {'error': str(e)}


class getRegisterForStudent(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('subject', type=str, help='Email address for Authentication')
            # parser.add_argument( 'password', type=str, help='Password for Authentication' )
            args = parser.parse_args()

            _userId = args['subject']
            # _password = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(
                "SELECT sj.SubjectName FROM Register r INNER join Subject s ON r.Subjectfk = s.Subjectfk INNER JOIN SubjectName sj ON sj.SubjectID = s.Subjectfk")

            if (cursor.rowcount > 0):
                data = cursor.fetchall()
                items_list = [];
                for item in data:
                    i = {
                        'SubjectName': item[0],

                    }
                    items_list.append(i)

                return {'status': 'true', 'subject': items_list}
        except Exception as e:
            return {'status': str(e)}


class getStudent(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('student', type=str, help='Email address for Authentication')
            # parser.add_argument( 'password', type=str, help='Password for Authentication' )
            args = parser.parse_args()

            _userId = args['student']
            # _password = args['password']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute("SELECT * FROM Student")

            if (cursor.rowcount > 0):
                data = cursor.fetchall()
                items_list = [];
                for item in data:
                    i = {
                        'S_Name': item[0],

                    }
                    items_list.append(i)

                return {'status': 'true', 'student': items_list}
        except Exception as e:
            return {'status': str(e)}


class SetStudentAnswer(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('answer', type=str, help='Email address for Authentication')
            parser.add_argument('homeworkset', type=str, help='Email address for Authentication')
            parser.add_argument('random', type=str, help='Email address for Authentication')
            # parser.add_argument( 'password', type=str, help='Password for Authentication' )
            args = parser.parse_args()

            _answer = args['answer']
            _homeworkset = args['homeworkset']
            _random = args['random']

            data_answer = json.loads(_answer)
            data_homeworkset = json.loads(_homeworkset)
            data_random = json.loads(_random)

            conn = mysql.connect()
            cursor = conn.cursor()

            # insert table student_answer
            count = 0;
            for index in data_answer['Student_answer']:
                print data_answer['Student_answer'][count]['Studentfk']
                print data_answer['Student_answer'][count]['Questionfk']
                print data_answer['Student_answer'][count]['Answerfk']
                cursor.execute(
                    "INSERT INTO `smartHomework`.`Student_answer` (`Studentfk`, `Questionfk`, `Answerfk`) VALUES ('%s', '%s', '%s');" % \
                    (data_answer['Student_answer'][count]['Studentfk'],
                     data_answer['Student_answer'][count]['Questionfk'],
                     data_answer['Student_answer'][count]['Answerfk']))
                count += 1

            print 'set answer success'
            print '**************************'

            # insert table homeworkset
            print data_homeworkset['HsetID']
            print data_homeworkset['Score']
            print data_homeworkset['SubmitDate']
            print data_homeworkset['Studentfk']

            cursor.execute(
                "INSERT INTO `smartHomework`.`Homeworkset` (`HsetID`, `Score`, `SubmitDate`, `Studentfk`) VALUES ('%s', '%s', '%s', '%s');" % \
                (data_homeworkset['HsetID'], data_homeworkset['Score'], data_homeworkset['SubmitDate'],
                 data_homeworkset['Studentfk']))

            print 'set homeworkset success'
            print '**************************'
            conn.commit()
            # insert table random

            c = 0;
            for index in data_random['random']:
                print data_random['random'][c]['Hsetfk']
                print data_random['random'][c]['Questionfk']
                cursor.execute(
                    "INSERT INTO `smartHomework`.`random` (`Hsetfk`, `Questionfk`) VALUES ('%s', '%s');" % \
                    (data_random['random'][c]['Hsetfk'],
                     data_random['random'][c]['Questionfk']))
                c += 1

            print 'set random success'
            print '**************************'
            conn.commit()

            return {'status': 'true'}
        except Exception as e:
            return {'status': str(e)}


class SetHomeworkset(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('json', type=str, help='Email address for Authentication')
            # parser.add_argument( 'password', type=str, help='Password for Authentication' )
            args = parser.parse_args()

            _json = args['json']
            data = json.loads(_json)

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(
                "INSERT INTO `smartHomework`.`Homeworkset` (`HsetID`, `Score`, `SubmitDate`, `Studentfk`) VALUES ('%s', '%s', '%s', '%s');" % \
                (data['HsetID'], data['Score'], data['SubmitDate'], data['Studentfk']))

            conn.commit()

            return {'status': 'true'}
        except Exception as e:
            return {'status': str(e)}


class SubjectOfTeacher(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('subject', type=str, help='Email address for Authentication')
            parser.add_argument('teacher', type=str, help='Password for Authentication')
            args = parser.parse_args()

            _userId = args['subject']
            _teacher = args['teacher']

            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(
                "SELECT sn.SubjectName , t.T_Name FROM Subject s INNER join Teacher t on t.TeacherID = s.Teacherfk INNER join SubjectName sn on s.Subjectfk = sn.SubjectID")

            if (cursor.rowcount > 0):
                data = cursor.fetchall()
                items_list = [];
                for item in data:
                    i = {
                        'SubjectName': item[0],
                        'T_Name': item[1],

                    }
                    items_list.append(i)

                return {'status': 'true', 'teacher': items_list}
        except Exception as e:
            return {'status': str(e)}


def getquestion():
    try:
        # Parse the arguments
        # parser = reqparse.RequestParser()
        # parser.add_argument('TeacherID', type=str)
        # args = parser.parse_args()
        #
        # _userId = args['TeacherID']

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM `Question` LIMIT 1000;")
        data = cursor.fetchall()

        items_list = [];
        for item in data:
            i = {
                'QuestionID': item[0],
                'Proposition': item[1],
                'Exercisefk': item[2],
                'Correct': item[3],
                'AnsDesc': item[4]
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getchapter():
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM `Chapter` LIMIT 1000;")
        data = cursor.fetchall()

        items_list = [];
        for item in data:
            i = {
                'ChapterID': item[0],
                'ChapterName': item[1],
                'Subjectfk': item[2]
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getsubjectName():
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM `smartHomework`.`SubjectName` LIMIT 1000;")
        data = cursor.fetchall()

        items_list = [];
        for item in data:
            i = {
                'SubjectID': item[0],
                'SubjectName': item[1]
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getanswer():
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM `smartHomework`.`Answer` LIMIT 1000;")
        data = cursor.fetchall()

        items_list = [];
        for item in data:
            i = {
                'AnswerID': item[0],
                'Choice': item[1],
                'Answer': item[2],
                'Questionfk': item[3]
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getcorrectAnswer():
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT * FROM `smartHomework`.`CorrectAns` LIMIT 1000;")
        data = cursor.fetchall()

        items_list = [];
        for item in data:
            i = {
                'CorrectID': item[0],
                'Answerfk': item[1],
                'AnsDesc': item[2]
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getexercise():
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "SELECT ExerID, Chapterfk, DATE_FORMAT(DoDate,'%d/%m/%Y'), DATE_FORMAT(ExpDate,'%d/%m/%Y'), AmountDo, Score, Level FROM `Exercise` LIMIT 1000")
        data = cursor.fetchall()

        items_list = [];
        for item in data:
            i = {
                'ExerID': item[0],
                'ChapterID': item[1],
                'DoDate': item[2],
                'ExpDate': item[3],
                'AmountDo': item[4],
                'Score': item[5],
                'Level': item[6]

            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def gethomeworkset(_studentid):
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "SELECT h.HsetID, h.Score, h.Studentfk, r.Questionfk, h.SubmitDate FROM smarthomework.homeworkset h RIGHT JOIN smarthomework.random r ON h.HsetID = r.Hsetfk WHERE h.Studentfk = '%s';" % (
                _studentid))

        items_list = [];
        for (HsetID, Score, Studentfk, Questionfk, SubmitDate) in cursor:
            i = {
                'HsetID': HsetID,
                'Score': Score,
                'Studentfk': Studentfk,
                'Questionfk': Questionfk,
                'SubmitDate' : str(SubmitDate)
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getallhomeworkset():
    try:
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute("SELECT h.HsetID, h.Score, h.Studentfk, r.Questionfk FROM smartHomework.homeworkset h RIGHT JOIN smarthomework.random r ON h.HsetID = r.Hsetfk;")

        items_list = [];
        for (HsetID, Score, Studentfk, Questionfk) in cursor:
            i = {
                'HsetID': HsetID,
                'Score': Score,
                'Studentfk': Studentfk,
                'Questionfk': Questionfk,
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getexamdate():
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "SELECT ExamID, Chapterfk, Description, DATE_FORMAT(ExamDate,'%d/%m/%Y') FROM `Test_exam` LIMIT 1000;")
        data = cursor.fetchall()

        items_list = [];
        for item in data:
            i = {
                'ExamID': item[0],
                'Chapterfk': item[1],
                'Description': item[2],
                'ExamDate': item[3]
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}
    return str


def getsubject(TeacherID):
    try:

        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(
            "SELECT SubjectName.SubjectID, SubjectName.SubjectName, subject.Teacherfk, "
            "subject.Subjectfk  FROM SubjectName INNER JOIN subject ON SubjectName.SubjectID = subject.Subjectfk WHERE subject.`Teacherfk` = '%s' LIMIT 1000" % (
                TeacherID))
        # data = cursor.fetchall()

        items_list = [];
        for (SubjectID, SubjectName, Teacherfk, Subjectfk) in cursor:
            i = {
                'SubjectID': SubjectID,
                'SubjectName': SubjectName,
                'Teacherfk': Teacherfk
            }
            items_list.append(i)

        return items_list

    except Exception as e:
        return {'error': str(e)}


class GetItemForStudent(Resource):
    def post(self):
        try:
            # return {'status': 'true','Exercise':getExercise()}
            return {'status': 'true', 'Exam': getexamdate(), 'Question': getquestion(), 'Subject': getsubjectName(),
                    'Chapter': getchapter(), 'Answer': getanswer(), 'CorrectAnswer': getcorrectAnswer(),
                    'Exercise': getexercise()}
        except Exception as e:
            return {'status': str(e)}


class GetItemForTeacher(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('teacherid', type=str, help='Email address for Authentication')
            # parser.add_argument( 'password', type=str, help='Password for Authentication' )
            args = parser.parse_args()

            _teacherid = args['teacherid']

            # return {'status': 'true','Exercise':getExercise()}
            return {'status': 'true', 'Exam': getexamdate(), 'Question': getquestion(),
                    'Subject': getsubject(_teacherid),
                    'Chapter': getchapter(), 'Answer': getanswer(), 'CorrectAnswer': getcorrectAnswer(),
                    'Exercise': getexercise(), "Homeworkset": getallhomeworkset()}
        except Exception as e:
            return {'status': str(e)}


class GetItemForParent(Resource):
    def post(self):
        try:
            parser = reqparse.RequestParser()
            parser.add_argument('studentid', type=str, help='Email address for Authentication')
            # parser.add_argument( 'password', type=str, help='Password for Authentication' )
            args = parser.parse_args()

            _studentid = args['studentid']

            return {'status': 'true', 'Exam': getexamdate(), 'Question': getquestion(), 'Subject': getsubjectName(),
                    'Chapter': getchapter(), 'Answer': getanswer(), 'CorrectAnswer': getcorrectAnswer(),
                    'Exercise': getexercise(), 'Homeworkset': gethomeworkset(_studentid)}
        except Exception as e:
            return {'status': str(e)}


api.add_resource(CreateUser, '/CreateUser')
api.add_resource(AuthenticateUser, '/AuthenticateUser')
api.add_resource(UpdateAnswerOfStudent, '/UpdateAnswerOfStudent')
api.add_resource(AddItem, '/AddItem')
api.add_resource(GetAlIQuestion, '/GetAlIQuestion')
api.add_resource(Login, '/Login')
api.add_resource(UpdateQuestion, '/UpdateQuestion')
api.add_resource(SubjectOfTeacher, '/SubjectOfTeacher')
api.add_resource(GetItemForStudent, '/GetItemForStudent')
api.add_resource(GetItemForTeacher, '/GetItemForTeacher')
api.add_resource(GetItemForParent, '/GetItemForParent')
api.add_resource(getStudent, '/getStudent')
api.add_resource(random, '/random')
api.add_resource(SetStudentAnswer, '/SetStudentAnswer')
api.add_resource(SetHomeworkset, '/SetHomeworkset')

if __name__ == '__main__':
    HOST = environ.get('SERVER_HOST', '0.0.0.0')
    try:
        PORT = int(environ.get('SERVER_PORT', '5555'))
    except ValueError:
        PORT = 5555
    app.run(HOST, PORT)
