package com.miner.mono.service;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@TestExecutionListeners(
        listeners = {
                DependencyInjectionTestExecutionListener.class,
                FlywayTestExecutionListener.class},
        mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@FlywayTest(invokeCleanDB = false)
public abstract class AbstractServiceTest {

}
