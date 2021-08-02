package com.team.shared.engine.engine.unmarshal;

import com.rits.cloning.Cloner;
import com.team.shared.dto.UserDTO;
import com.team.shared.engine.data.execute.AfterExecutionOrderAndTransactionDTO;
import com.team.shared.engine.data.stock.Stocks;
import com.team.shared.engine.data.user.Users;
import com.team.shared.engine.data.xjc.generated.RseStocks;
import com.team.shared.engine.engine.Engine;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor public class EngineInstance
        implements EngineUnmarshallable, EngineValidator {

    private Stocks stocks;

    private Users users;

    private List<UserDTO> signedInUsers;

    private AfterExecutionOrderAndTransactionDTO
            afterExecutionOrderAndTransactionDTO;

    public EngineInstance(boolean initFromEngine) {
        if (initFromEngine) {
            stocks = Engine.getStocksForced();
            users = Engine.getUsersForced();
            signedInUsers = Engine.getSignedInUsers();
            afterExecutionOrderAndTransactionDTO =
                    Engine.getAfterExecutionOrderAndTransactionDTO();
        }
    }

    public void addStocks(RseStocks rseStocks) {
        addStocks(stocks, rseStocks);
    }

    public void transferToEngine() {
        Cloner cloner = new Cloner();
        Engine.setStocks(cloner.deepClone(stocks));
        Engine.setUsers(cloner.deepClone(users));
        Engine.setSignedInUsers(cloner.deepClone(signedInUsers));
        Engine.setAfterExecutionOrderAndTransactionDTO(
                cloner.deepClone(afterExecutionOrderAndTransactionDTO));
    }

}
