/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

 
import com.example.entity.Processo;
import com.example.mq.ProcessoMQ;
import com.example.repository.IProcessoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
 
@RestController
public class ServiceControllerProcesso {
 
    @Autowired
    private IProcessoRepository repo;
 
 
    // CREATE
    @RequestMapping("/processo/create")
    @ResponseBody
    public String createProcesso() {
        Processo processo = new Processo("Saba test", 10, 0);
        try {
            repo.save(processo);
            ProcessoMQ.write(processo.getId());
            
        } catch (Exception e) {
            return e.getMessage();
        }
        return "creation successful: " + String.valueOf(processo.getId());
    }
 
//    // READ
    @RequestMapping("/processo/read")
    @ResponseBody
    public Processo readMovie(@RequestParam("id")long id) {
        Processo processo;
        try {
            processo = repo.findOne(id);
        } catch (Exception e) {
            return null;
        }
        if (processo == null) {
            String errorMst = "Nessun processo trovato con id " + id;
            return null;
        } else {
            return processo;
            //return processo.getId()+ ":" + processo.getProcessId()+":"+ processo.getTesto()+":"+ processo.getProcessato();
        }
    }

//    // UPDATE
    @RequestMapping("/processo/update")
    @ResponseBody
    public Processo readMovie(@RequestParam("id")long id, @RequestParam("processId") int processId, @RequestParam("testo") String testo, @RequestParam("processato") int processato) {
        Processo processo;
        try {
            processo = repo.findOne(id);
            processo.setTesto(testo);
            processo.setProcessId(processId);
            processo.setProcessato(processato);
            repo.save(processo);
        } catch (Exception e) {
            return null;
        }
        return processo;    
        //return processo.getId()+ ":" + processo.getProcessId()+":"+ processo.getTesto()+":"+ processo.getProcessato();
    }


// 
    @RequestMapping("/processo/readAll")
    public List<Processo> getProcessi() {
        List<Processo> processo = (List<Processo>) repo.findAll();
        return processo;
    }
}
