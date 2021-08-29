package com.company.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.company.vo.Data;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RulesCompiler {
	/*
	* When you have to read spreadsheet rules from the resources directory, and you have multiple ones
	
    ResourcePatternResolver resourcePatternResolver;

    public RulesCompiler(@Autowired ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public List<String> compileRules() {
    	
    	 
        List<String> rulesList = new ArrayList<>();

        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:rules/*.xls");

            for(Resource r : resources) {
                log.info("Compiling resource file --> " + r.getFilename());
                //InputStream is= new FileInputStream(r.getFile());
                InputStream is = getClass().getResourceAsStream("/rules/" + r.getFilename());
                SpreadsheetCompiler sc = new SpreadsheetCompiler();
                String rules=sc.compile(is, InputType.XLS);
                log.info("Compiled drools file == " + rules);

                rulesList.add(rules);
            }

        } catch (IOException e) {
            log.error("Error while reading the resource", e);
            return null;
        }

        return rulesList;
    }
    */
	
	public List<String> compileRules(String ruleFilepath) {
		List<String> rulesList = new ArrayList<>();
		
		InputStream is = null;
        try {
            is= new FileInputStream(ruleFilepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SpreadsheetCompiler sc = new SpreadsheetCompiler();
        String rules=sc.compile(is, InputType.XLS);
        
        log.info("Compiled drools file == " + rules);

        rulesList.add(rules);
		
		return rulesList;
	}
	
}