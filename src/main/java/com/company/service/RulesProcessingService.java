package com.company.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.command.Command;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.vo.Customer;

@Slf4j
@Service
public class RulesProcessingService {
	
	@Autowired
	RulesCompiler rulesCompiler;

    public Customer applyRules(Customer req) {
    	    	
        StatelessKieSession kSession = rulesCompiler.getKSession();

        log.info("Executing rules ...");

        List<Command> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(req));
        cmds.add(CommandFactory.newFireAllRules());
        cmds.add(CommandFactory.newGetObjects(new ClassObjectFilter(Customer.class), "output"));

        ExecutionResults results = kSession.execute(CommandFactory.newBatchExecution(cmds));

        List<Customer> data_out = (List<Customer>) (Collection<?>) results.getValue("output");

        log.info("rules output = " + data_out);

        return data_out.get(0);
    }
}
