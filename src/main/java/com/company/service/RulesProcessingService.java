package com.company.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.command.Command;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.stereotype.Service;

import com.company.vo.ApiRequest;
import com.company.vo.Customer;

@Slf4j
@Service
public class RulesProcessingService {

    public List<Customer> applyRules(ApiRequest req) {



        InputStream is = null;
        try {
            is= new FileInputStream(req.getRulefilepath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SpreadsheetCompiler sc = new SpreadsheetCompiler();
        String rules=sc.compile(is, InputType.XLS);

        StringBuffer drl = new StringBuffer(rules);
        log.info("drool file == " + drl);

        StatelessKieSession kSession = new KieHelper().addContent(rules, ResourceType.DRL).build().newStatelessKieSession();

        Customer customer = new Customer(Customer.CustomerType.BUSINESS, 2);

        log.info("Executing rules ...");

        List<Command> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(customer));
        cmds.add(CommandFactory.newFireAllRules());
        cmds.add(CommandFactory.newGetObjects(new ClassObjectFilter(Customer.class), "output"));

        ExecutionResults results = kSession.execute(CommandFactory.newBatchExecution(cmds));

        List customer_out = (List<Customer>) (Collection<?>) results.getValue("output");

        log.info("rules output = " + customer_out);

        return customer_out;
    }
}
