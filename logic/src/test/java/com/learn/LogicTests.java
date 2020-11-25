package com.learn;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogicTests {


    @Test
    public void operationServiceWillReturnCorrectValueTest() {
        assertThat(OperationService.calculate(new Operation(1, 2, Operation.OperationType.ADD)))
            .isEqualTo(3);
        assertThat(OperationService.calculate(new Operation(1, 2, Operation.OperationType.MULT)))
            .isEqualTo(2);
        assertThat(OperationService.calculate(new Operation(1, 2, Operation.OperationType.SUB)))
            .isEqualTo(-1);
    }
}
