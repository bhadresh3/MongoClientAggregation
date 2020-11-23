package com.system27.mongo.Repo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.system27.mongo.Model.Person;
import com.system27.mongo.MongoApplication;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonRepo {

    private final MongoDatabase db = MongoApplication.getDatabase();

    public List<Person> findAll() {
        MongoCollection<Document> collection = db.getCollection("people");
        List<Person> people = new ArrayList<>();
        for(Document document : collection.find()){
            people.add(new Person(
                    document.get("_id").toString(),
                    document.getString("name"),
                    document.getInteger("salary"),
                    document.getInteger("age"),
                    document.getList("likes", String.class)));
        }
        return people;
    }

    public void add(Person person) {
        MongoCollection<Document> collection = db.getCollection("people");
        Document p = new Document();
        p.append("name", person.getName());
        p.append("age", person.getAge());
        p.append("salary", person.getSalary());
        p.append("likes", person.getLikes());
        try {
            collection.insertOne(p);
        }catch (Exception e){
            throw new RuntimeException("something went wrong" + e.getMessage());
        }
    }

    public void update(Person person) {
        MongoCollection<Document> collection = db.getCollection("people");
        Document p = new Document();
        p.append("name", person.getName());
        p.append("age", person.getAge());
        p.append("salary", person.getSalary());
        p.append("likes", person.getLikes());
        try{
            BasicDBObject filter = new BasicDBObject("_id", new ObjectId(person.getId()));
            collection.updateOne(filter, new BasicDBObject("$set", p));
        }catch (Exception e){
            throw new RuntimeException("something went wrong" + e.getMessage());
        }

    }

    public void delete(String id) {
        MongoCollection<Document> collection = db.getCollection("people");
        try{
            BasicDBObject filter = new BasicDBObject("_id", new ObjectId(id));
            collection.deleteOne(filter);
        }catch (Exception e){
            throw new RuntimeException("something went wrong" + e.getMessage());
        }
    }

    public List<Person> findAllPersonByPage(int pageNo, int pageSize, List<String> sortBy) {

        MongoCollection<Document> collection = db.getCollection("people");
        List<Person> people = new ArrayList<>();
        Document sortD = new Document();
        for(String sort : sortBy){
            sortD.append(sort, 1);
        }

        for(Document document : collection
                .find()
                .sort(sortD)
                .skip(pageNo*pageSize)
                .limit(pageSize)
        ){
            people.add(new Person(
                    document.get("_id").toString(),
                    document.getString("name"),
                    document.getInteger("salary"),
                    document.getInteger("age"),
                    document.getList("likes", String.class)));
        }
        return people;

    }

    public double avgSalary() {
//        $group: {
//            _id: null,
//                    countBy: {
//            $avg: "$salary"
//        }

        MongoCollection<Document> collection = db.getCollection("people");
        List<BasicDBObject> operations = new ArrayList<>();
        BasicDBObject avg = new BasicDBObject("$avg", "$salary");
        BasicDBObject group = new BasicDBObject();
        group.append("_id",null);
        group.append("avg", avg);
        BasicDBObject grp = new BasicDBObject("$group", group);
        operations.add(grp);
        System.out.println("-----------------");
        System.out.println(operations);
        System.out.println("-----------------");
        AggregateIterable<Document> aggregate = collection.aggregate(operations);
        return Objects.requireNonNull(aggregate.first()).get("avg", Double.class);
    }

    public List<Person> findByLikes(String[] likes) {
        /*
        {"likes":{$elemMatch:{$in:['Chocolate','Strawberry']}}}
         */
        MongoCollection<Document> collection = db.getCollection("people");
        List<Person> people = new ArrayList<>();
        BasicDBObject in = new BasicDBObject("$in",likes);
        BasicDBObject elem = new BasicDBObject("$elemMatch", in);
        BasicDBObject likz = new BasicDBObject("likes", elem);
        for(Document document : collection.find(likz)//.projection(dbObj)
         ){
            people.add(new Person(
                    document.get("_id").toString(),
                    document.getString("name"),
                    document.getInteger("salary"),
                    document.getInteger("age"),
                    document.getList("likes", String.class)));
        }
        return people;

    }
}
