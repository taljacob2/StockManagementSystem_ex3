package com.team.shared.engine.engine.backup;

import com.team.shared.dto.UserDTO;
import com.team.shared.engine.data.execute.AfterExecutionOrderAndTransactionDTO;
import com.team.shared.engine.data.stock.Stocks;
import com.team.shared.engine.data.user.Users;
import com.team.shared.engine.engine.Engine;
import lombok.Data;

import java.util.List;

@Data public class EngineBackup {

    private Stocks stocks;

    private Users users;

    private List<UserDTO> signedInUsers;

    private AfterExecutionOrderAndTransactionDTO
            afterExecutionOrderAndTransactionDTO;

    public EngineBackup() {
        stocks = Engine.getStocksForced();
        users = Engine.getUsersForced();
        signedInUsers = Engine.getSignedInUsers();
        afterExecutionOrderAndTransactionDTO =
                Engine.getAfterExecutionOrderAndTransactionDTO();
    }

}
