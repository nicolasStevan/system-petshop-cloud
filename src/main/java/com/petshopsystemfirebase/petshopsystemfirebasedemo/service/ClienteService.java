package com.petshopsystemfirebase.petshopsystemfirebasedemo.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.Api;
import com.petshopsystemfirebase.petshopsystemfirebasedemo.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ClienteService {
    private static final String COLLECTION_NAME = "clientes";

    public String saveCliente(Cliente cliente) throws ExecutionException, InterruptedException{
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document().set(cliente);

        return collectionApiFuture.get().getUpdateTime().toString();
    }
}
