#导入包
#pymysql是python3连接数据库的包
#tkinter是python的GUI界面包
#tkinter.messagebox是消息提示框包
import pymysql
import tkinter as tk
import tkinter.messagebox

#数据库添加操作
def db_add():
    #连接数据库
    db=pymysql.connect(host="localhost",port=3306,user="root",password="123a123a",db="test")
    #创建光标
    cursor=db.cursor()
    #编写SQL语句
    sql = "insert into book(title,author,price) values('%s','%s',%s)"%(v1.get(),v2.get(),v3.get())
    #执行SQL语句，并且输出完成提示信息，否则回滚
    try:
        cursor.execute(sql)
        db.commit()
        tkinter.messagebox.showinfo("提示","数据添加成功")
    except:
        db.rollback()
    #关闭数据库连接，防止泄露
    db.close()

#数据库删除操作
def db_delete():
    db=pymysql.connect(host="localhost",port=3306,user="root",password="123a123a",db="test")
    cursor=db.cursor()
    sql = "delete from book where title='%s'" % (v4.get())
    try:
        cursor.execute(sql)
        db.commit()
        tkinter.messagebox.showinfo("提示","数据删除成功")
    except:
        db.rollback()
    db.close()

#数据库更新操作
def db_update():
    db = pymysql.connect(host="localhost", port=3306, user="root", password="123a123a", db="test")
    cursor = db.cursor()
    sql="update book set price='%s' where title='%s'"%(v6.get(),v5.get())
    try:
        cursor.execute(sql)
        db.commit()
        tkinter.messagebox.showinfo("提示","数据更新成功！")
    except:
        db.rollback()
    db.close()

#数据库输出所有记录
def db_select_all():
    db = pymysql.connect(host="localhost", port=3306, user="root", password="123a123a", db="test")
    cursor = db.cursor()
    sql = "select * from book"
    try:
        #MySQL删除只删除数据不删除空间，导致id列自增会出现断层，需要重新生成后输出
        db_initiate()
        print("序号排列完成！")
        cursor.execute(sql)
        #输出全部结果
        results=cursor.fetchall()
        #输出对应列的数据
        for row in results:
            id = row[0]
            title = row[1]
            author = row[2]
            price = row[3]
            print("id=%d,title=%s,author=%s,price=%s" % (id,title, author, price))
    except:
        return
    db.close()

#数据库模糊条件查询
def db_select():
    db = pymysql.connect(host="localhost", port=3306, user="root", password="123a123a", db="test")
    cursor = db.cursor()
    sql = "select title,author,price from book where title like'%s'"%('%'+v7.get()+'%')
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        for row in results:
            title = row[0]
            author = row[1]
            price = row[2]
            tkinter.messagebox.showinfo("提示","title=%s,author=%s,price=%s" % (title, author, price))
    except:
        return

#重新生成自增id列
def db_initiate():
    db = pymysql.connect(host="localhost", port=3306, user="root", password="123a123a", db="test")
    cursor = db.cursor()
    #原理是删除原先的id列，然后重新生成一个自增id列
    sql = "alter table book drop id;"
    sql2= "alter table book add id int primary key not null auto_increment first;"
    try:
        cursor.execute(sql)
        db.commit()
        cursor.execute(sql2)
        db.commit()
    except:
        return
    db.close()

#添加书籍界面
def menu_add():
    #构建全集变量，方便上面的函数调用
    global window_function
    global v1, v2, v3
    #生成窗口
    window_function=tk.Tk()
    #窗口标题
    window_function.title("图书数据库系统")
    #窗口大小
    window_function.geometry('400x300')
    #生成标签
    tk.Label(window_function, text="添加新书", font=("黑体", 20)).grid(row=0,column=1,pady=10)
    tk.Label(window_function,text="请输入书名：").grid(row = 1,column =0,padx=20,pady=20)
    tk.Label(window_function,text="请输入作者名：").grid(row = 2,column =0,padx=20,pady=20)
    tk.Label(window_function,text="请输入价格：").grid(row = 3,column =0,padx=20,pady=20)
    #定义变量记录输入信息
    v1=tk.StringVar()
    v2=tk.StringVar()
    v3=tk.StringVar()
    #生成输入框
    entry1=tk.Entry(window_function,show=None,textvariable=v1).grid(row = 1,column =1)
    entry2=tk.Entry(window_function,show=None,textvariable=v2).grid(row = 2,column =1)
    entry3=tk.Entry(window_function,show=None,textvariable=v3).grid(row = 3,column =1)
    #生成按钮
    button = tk.Button(window_function, text="添加", command=db_add).grid(row = 4,column =1)
    button2 = tk.Button(window_function, text="返回", command=pagechaneg_main).grid(row = 4,column =2,padx=50)
    #显示窗口
    window_function.mainloop()

#删除书籍界面
def menu_delete():
    global window_function
    global v4
    window_function=tk.Tk()
    window_function.title("图书数据库系统")
    window_function.geometry('400x300')
    tk.Label(window_function, text="删除书籍", font=("黑体", 20)).grid(row=0,column=1,pady=20)
    tk.Label(window_function,text="请输入书名：").grid(row = 1,column =0,padx=20)
    v4 =tk.StringVar()
    entry1=tk.Entry(window_function,show=None,textvariable=v4).grid(row = 1,column =1,pady=40)
    button = tk.Button(window_function, text="删除", command=db_delete).grid(row = 2,column =1)
    button2 = tk.Button(window_function, text="返回", command=pagechaneg_main).grid(row = 2,column =2,padx=50)
    window_function.mainloop()

#更新书籍界面
def menu_update():
    global window_function
    global v5,v6
    window_function=tk.Tk()
    window_function.title("图书数据库系统")
    window_function.geometry('400x300')
    tk.Label(window_function, text="更新书籍", font=("黑体", 20)).grid(row=0,column=1,pady=20)
    tk.Label(window_function,text="请输入书名：").grid(row = 1,column =0,padx=20,pady=20)
    tk.Label(window_function,text="请输入价格：").grid(row = 2,column =0,padx=20,pady=20)
    v5=tk.StringVar()
    v6=tk.StringVar()
    entry1=tk.Entry(window_function,show=None,textvariable=v5).grid(row = 1,column =1)
    entry2=tk.Entry(window_function,show=None,textvariable=v6).grid(row = 2,column =1)
    button = tk.Button(window_function, text="更新", command=db_update).grid(row = 3,column =1)
    button2 = tk.Button(window_function, text="返回", command=pagechaneg_main).grid(row = 3,column =2,padx=50)
    window_function.mainloop()

#条件查找书籍界面
def menu_select():
    global window_function
    global v7
    window_function=tk.Tk()
    window_function.title("图书数据库系统")
    window_function.geometry('400x300')
    tk.Label(window_function, text="查找书籍", font=("黑体", 20)).grid(row=0,column=1,pady=20)
    tk.Label(window_function,text="请输入书名：").grid(row = 1,column =0,padx=20)
    v7 =tk.StringVar()
    entry1=tk.Entry(window_function,show=None,textvariable=v7).grid(row = 1,column =1,pady=40)
    button = tk.Button(window_function, text="查找", command=db_select).grid(row = 2,column =1)
    button2 = tk.Button(window_function, text="返回", command=pagechaneg_main).grid(row = 2,column =2,padx=50)
    window_function.mainloop()

#添加书籍界面跳转
def pagechange_add():
    #销毁画布
    window.destroy()
    #生成新界面
    menu_add()

#删除书籍界面跳转
def pagechange_delete():
    window.destroy()
    menu_delete()

#更新书籍界面跳转
def pagechange_update():
    window.destroy()
    menu_update()

#条件查询书籍界面跳转
def pagechange_select():
    window.destroy()
    menu_select()

#主界面跳转
def pagechaneg_main():
    window_function.destroy()
    mainpage()

#主界面
def mainpage():
    global window
    window = tk.Tk()
    window.title("图书数据库系统")
    window.geometry('400x300')
    #生成画布，销毁后生成新的画布实现跳转
    page = tk.Frame(window)
    page.pack()
    tk.Label(window, text="欢迎使用图书数据库系统", font=("黑体", 20)).pack(pady=10)
    button1 = tk.Button(window, text="添加书籍", command=pagechange_add).pack(pady=10)
    button2 = tk.Button(window, text="删除书籍", command=pagechange_delete).pack(pady=10)
    button3 = tk.Button(window, text="修改书籍", command=pagechange_update).pack(pady=10)
    button4 = tk.Button(window, text="查找书籍", command=pagechange_select).pack(pady=10)
    window.mainloop()

#主函数生成主界面
if __name__ == '__main__':
    mainpage()