package com.petshopsystemfirebase.petshopsystemfirebasedemo.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.Api;
import com.petshopsystemfirebase.petshopsystemfirebasedemo.entity.Animal;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class AnimalService {
    private static final String COLLECTION_NAME ="animal";

    private static long nextId = 1;

    private synchronized long getNextId() {
        return nextId++;
    }

    public String saveAnimal(Animal animal) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        animal.setId(getNextId()); // Defina o ID incremental
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(String.valueOf(animal.getId())).set(animal);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public Animal getAnimalDetailsById(long id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(String.valueOf(id));
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Animal animal = null;
        if (document.exists()) {
            animal = document.toObject(Animal.class);
            return animal;
        } else {
            return null;
        }
    }

    public List<Animal> getAnimalDetails() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReference = dbFirestore.collection(COLLECTION_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<Animal> animalList = new ArrayList<>(); // Inicialize a lista aqui

        while(iterator.hasNext()){
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();

            if(document.exists()){
                Animal animal = document.toObject(Animal.class);
                animal.setId(document.getLong("id")); // Obtenha o ID como Long
                animalList.add(animal);
            }
        }
        return animalList;
    }


    public String updateAnimal(Animal animal) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Map<String, Object> updates = new HashMap<>();
        updates.put("idade", animal.getIdade());
        updates.put("nome", animal.getNome());
        updates.put("peso", animal.getPeso());
        updates.put("raca", animal.getRaca());

        // Atualiza apenas os campos especificados sem substituir o documento inteiro
        ApiFuture<WriteResult> updateResult = dbFirestore.collection(COLLECTION_NAME).document(String.valueOf(animal.getId())).update(updates);

        return updateResult.get().getUpdateTime().toString();
    }


    public String deleteAnimal(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApiFuture=dbFirestore.collection(COLLECTION_NAME).document(id).delete();

        return "Dado de id "+id+" Foi Deletado com sucesso";
    }


}
