from flask import *
from DBConnection import Db
from datetime import datetime

app = Flask(__name__)
db=Db()
app.secret_key="hai"
static_path=""
total_sum=0
@app.route('/')
def login():
    return render_template("login_temp.html")
@app.route("/login_post",methods=['post'])
def login_post():
    db=Db()
    username=request.form['t1']
    password=request.form['t2']
    qry="SELECT * FROM login WHERE username='"+username+"' AND password='"+password+"'"
    res=db.selectOne(qry)
    if res is not None:
        if res['usertype']=="admin":
            print("hhh")
            return render_template("adindex.html")
        else:
            return "<script>alert('No user found');window.location='/'</script>"



@app.route("/add_product")
def add_product():
    return render_template("add_product.html")


@app.route("/add_product_post",methods=['post'])
def  add_product_post():
    pro_name=request.form['t1']
    type=request.form['s1']
    price=request.form['t2']
    recipe_name=request.form['rec']
    recipe=request.form['re']
    s = request.files['file']
    print(s)
    img=str.split(s.filename,'.')
    im=img[len(img)-1]
    i=pro_name+"."+im
    s.save("C:\\Users\\SHAMNA\\PycharmProjects\\grocery_management_final\\src\\static\\media\\"+i)
    print(pro_name,i)

    s1 = request.files['file1']
    img1 = str.split(s1.filename, '.')
    im1 = img1[len(img1) - 1]
    ii = recipe_name + "." + im1
    s1.save("C:\\Users\\SHAMNA\\PycharmProjects\\grocery_management_final\\src\\static\\media\\" + ii)
    qry="INSERT INTO `product`(`product_name`,`type`,`price`,`Recipies_name`,Recipies,photo,rec_pic)VALUES('"+pro_name+"','"+type+"','"+price+"','"+recipe_name+"','"+recipe+"','"+i+"','"+ii+"')"
    res=db.insert(qry)
    print(res)
    return render_template("add_product.html")


@app.route("/view_product")
def view_product():
    qry="SELECT * FROM `product`"
    res=db.select(qry)
    print(res)
    return render_template('view_product.html',data=res)


@app.route("/view_user")
def view_user():
    qry="SELECT * FROM `user`"
    res=db.select(qry)
    return render_template('view_user.html',data=res)



@app.route("/view_order")
def view_order():
    #qry="SELECT `user`.`name`, `product`.`product_name`,`order`.`order_date`,`order`.`quantity`,`order`.`total_amount`,`order`.`status` FROM `product` INNER JOIN `order` ON `order`.`pro_id`=`product`.`id` INNER JOIN `user` ON `user`.`id`=`order`.`login_user_id`  "
    # qry="SELECT `user`.`name`,`product`.`product_name`,`cart`.* FROM `cart`,`user`,`product`"
    #qry = "SELECT `user`.`name`,`product`.`product_name`,`cart`.`id`,`cart`.`date`,`cart`.`quantity`,`cart`.`price`,`cart`.`status` FROM `cart`,`user`,`product` where cart.status='ordered'"
    qry = "SELECT `user`.`name`,`product`.`product_name`,`cart`.`id`,`cart`.`date`,`cart`.`quantity`,`cart`.`price`,`cart`.`status` FROM `cart` INNER JOIN product on cart.pid=product.id INNER JOIN `user` on `user`.log_id=cart.uid  where cart.status='ordered'"

    res=db.select(qry)
    print(res)
    return render_template('view_order.html',data=res)





@app.route("/edit_product/<pid>")
def edit_product(pid):
    qry=" SELECT * FROM product WHERE id='"+pid+"'"
    res=db.selectOne(qry)
    session['eid'] = pid
    return render_template("edit_product.html",data=res)
@app.route("/edit_product_post",methods=['post'])
def edit_product_post():
    pid=request.form['pid']
    pro_name = request.form['t1']
    type = request.form['s1']
    price = request.form['t2']
    quantity = "0"
    s = request.files['file']
    print(s)
    img = str.split(s.filename, '.')
    im = img[len(img) - 1]
    i = pro_name + "." + im
    print(s, quantity)
    s.save("C:\\Users\\91996\\Desktop\\grocery\\static\\media\\" + i)
    print(pro_name, i)
    qry="UPDATE `product` set `product_name`='"+pro_name+"',`type`='"+type+"',`price`='"+price+"',`quantity`='"+quantity+"',Photo='"+i+"' WHERE `id`='"+pid+"' "
    db.update(qry)
    return "<script>alert('Notification updated successfully');window.location='/view_product'</script>"

@app.route("/del_product/<pid>")
def del_product(pid):
    qry="DELETE FROM product WHERE id='"+pid+"'"
    db.delete(qry)
    return view_product()
@app.route("/delivery_status/<id>")
def delivery_status(id):
    qry="update cart set status='delivered' where id="+id+""
    db.update(qry)
    return view_order()

@app.route('/adm')
def adm():
    return render_template("adindex.html")

@app.route('/home')
def home():
    return render_template("index.html")



'''.............................................................Android part...................................................'''
@app.route('/login', methods=['POST'])
def adlogin():
    email = request.form['email']
    password = request.form['password']

    qry=("SELECT * FROM`login` WHERE username='"+email+"' AND password='"+password+"'")
    s = db.selectOne(qry)
    if s is not None:
        return jsonify(status="ok",lid=s['id'],type=s['usertype'])
    else:
        return jsonify(status= "no")

@app.route('/user',methods=["post"])
def user():
    name=request.form['name']
    email=request.form['email']
    phone=request.form['phone']
    qry="INSERT INTO login (`username`,`password`,type) VALUES ('"+email+"','"+phone+"','user')"
    res=request.form['id']
    r=db.insert(qry)
    qry1="INSERT INTO USER(`name`,`email`,`phone`,`login_id`)VALUES('"+name+"','"+email+"','"+phone+"','"+str(r)+"')"
    res1=db.insert(qry1)
    return jsonify(status="ok")



@app.route('/view_profile',methods=['post'])
def us_view_profile():
    a=request.form["lid"]
    qry = "SELECT * FROM user WHERE log_id='" + str(a) + "'"
    res = db.selectOne(qry)
    print(res)
    return jsonify(status="ok",uname=res['name'],uphone=res['phone'],uplace=res['place'],upost_code=res['post_code'],uemail=res['email'],lid=res['log_id'])



@app.route('/view_quality',methods=['post'])
def viewquality():
    db = Db()
    qq = "select  product.*,quality.* from product inner join quality on product.id=quality.pro_id"
    res = db.select(qq)
    print(res)
    return jsonify(status="ok", data=res)

@app.route('/view_order',methods=['post'])
def vieworder():
    a = request.form["lid"]
    qq = "SELECT `product`.`product_name`,`order`.`order_date`,`order`.`quantity` FROM`product` INNER JOIN `order` ON `product`.`id`=`order`.`pro_id` WHERE `login_user_id`='"+str(a)+"'"
    res = db.select(qq)
    print(res)
    return jsonify(status="ok", data=res)


@app.route('/view_product',methods=['post'])
def viewproduct():
    #qq = "select * from product"
    qq="select  product.* from product inner join shoping_list on product.id=shoping_list.pid where shoping_list.status='pending'"
    res = db.select(qq)
    print(res)
    return jsonify(status="ok", data=res)
@app.route('/view_product1',methods=['post'])
def viewproduct1():
    qq = "select * from product"
    #qq="select * from product inner join shoping_list on product.id=shoping_list.pid where shoping_list.status='pending'"
    res = db.select(qq)
    print(res)
    return jsonify(status="ok", data=res)





@app.route("/register",methods=['post'])
def register_post():
    try:
        name=request.form['name']
        phone_no = request.form['phone']
        place = request.form['place']
        post = request.form['post']
        email_id = request.form['email']
        password = request.form['password']
        qry1="INSERT INTO `login`(`username`,`password`,`usertype`)VALUES('"+email_id+"','"+password+"','user')"
        res1=db.insert(qry1)
        qry="INSERT INTO `USER`(`NAME`,`phone`,`place`,`post_code`,`email`,password,log_id)VALUES('"+name+"','"+phone_no+"','"+place+"','"+post+"','"+email_id+"','"+password+"','"+str(res1)+"')"
        res=db.insert(qry)
        return jsonify({'status':'ok'})
    except Exception as e:
        print(str(e))
        return jsonify({'status':'not ok'})




@app.route('/addorder',methods=['post'])
def addorder():
    try:
       global total_sum
       date=datetime.now()
       quantity=request.form['qty']
       sid=request.form['lid']
       pid=request.form['pid']
       print(quantity)
       print(sid)
       print(pid)
       q="select * from shoping_list where pid="+str(pid)+" and status='pending'"
       res=db.selectOne(q)
       print(res)
       if len(res)>0:
           q1="select * from product where id='"+str(res['pid'])+"'"
           res1=db.selectOne(q1)
           price=float(res1['price'])*float(quantity)
           total_sum=total_sum+price
           print("product price",total_sum)
           q2="insert into cart values(null,'"+sid+"','"+pid+"','"+quantity+"','"+str(price)+"',curdate(),'pending')"
           res3=db.insert(q2)
           return jsonify({'status':'ok','sum':str(total_sum)})
    except:
        return jsonify({'status': 'not ok'})

@app.route('/removecart',methods=['post'])
def removecart():
    global total_sum
    sid=request.form['lid']
    pid=request.form['pid']
    print(sid)
    print(pid)
    try:
        q="select * from cart where pid='"+str(pid)+"' and uid='"+str(sid)+"'and status='pending'"
        res=db.select(q)
        print(res)
        if len(res)>0:
            for i in res:
                print(str(i['id']))
                total_sum = total_sum - float(str(i['price']))
                q1="delete from cart where id="+str(i['id'])+""
                db.delete(q1)

            return jsonify({'status':'ok','sum':str(total_sum)})
    except Exception as e:
        print(e)
        return jsonify({'status': 'not ok'})
@app.route('/finalorder',methods=['post'])
def finalorder():
   global total_sum
   try:
       sid = request.form['id']
       q="select * from cart where status='pending' and uid='"+str(sid)+"'"
       res=db.select(q)
       if res:
           print(res)
           for i in res:
               q2 = "update shoping_list set status='ordered' where pid='" + str(i['pid']) + "' and status='pending'"
               db.update(q2)
       q1="update cart set status='ordered' where uid='"+str(sid)+"' and status='pending'"
       db.update(q1)
       total_sum=0
       return jsonify({'status': 'success','sum':str(total_sum)})
   except Exception as e:
       return jsonify({'status': 'not ok'})

@app.route("/del_order")
def del_order():
    id = request.POST["id"]
    qry="DELETE FROM order WHERE id='"+id+"'"
    db.delete(qry)
    return jsonify({'Status': ' ok'})


@app.route("/recipe",methods=['post'])
def recipe():
    #qry="select * from product"
    qry="SELECT * FROM product WHERE id NOT IN (SELECT pid FROM shoping_list)"
    # qry="SELECT * FROM product WHERE id NOT IN (SELECT pid FROM shoping_list) AND id NOT IN (SELECT pid FROM cart WHERE STATUS='ordered' )"
    res=db.select(qry)
    return  jsonify(status="ok", data=res)

if __name__ == '__main__':
    app.run(host="0.0.0.0",port=5000)
