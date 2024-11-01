package com.example.consistenthashingalgorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {

    private final TreeMap<Long, String> ring;
    private final int replicas;
    private final MessageDigest md;


    public ConsistentHashing(int numOfReplicas) throws NoSuchAlgorithmException{
        this.ring = new TreeMap<>();
        this.replicas = numOfReplicas;
        this.md = MessageDigest.getInstance("MD5");
    }

    public void addServer(String server){
        for (int i = 0; i < this.replicas; i++) {
            long hash = generateHash(server + i);
            ring.put(hash, server);
        }

        System.out.println(server + " Added successfully (Virtual Servers too !!) :)");
    }

    public void removeServer(String server){
        for (int i = 0; i < this.replicas; i++) {
            long hash = generateHash(server + i);
            ring.remove(hash);
        }
    }

    public String getServer(String key){
        if (ring.isEmpty()){
            return null;
        }

        long hash = generateHash(key);
        if(!ring.containsKey(hash)){
            //Find the closest hash that is greater than the current hash
            //This implies that we find the closest right-most server to whom we can assign this key
            SortedMap<Long, String> tailMap = ring.tailMap(hash);

            //if there's no element present on the right side, we wrap around the ring return the first server
            hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }

        //returns the server which is associated with the hash
        return ring.get(hash);
    }

    public long GetHash(String key){
        return generateHash(key);
    }

    private long generateHash(String key){
        md.reset();
        md.update(key.getBytes(StandardCharsets.UTF_8));

        byte[] digest = md.digest();

        final long hash = ((long) (digest[3] & 0xFF) << 24) |
                ((long) (digest[2] & 0xFF) << 16) |
                ((long) (digest[1] & 0xFF) << 8) |
                ((long) (digest[0] & 0xFF));


        return hash;
    }

}
