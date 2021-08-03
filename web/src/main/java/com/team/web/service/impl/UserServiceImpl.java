package com.team.web.service.impl;

import com.team.shared.dto.CompanyDTO;
import com.team.shared.dto.UserDTO;
import com.team.shared.dto.WalletBalanceDTO;
import com.team.shared.engine.data.stock.Stock;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.user.holding.item.Item;
import com.team.shared.engine.data.user.role.Role;
import com.team.shared.engine.data.user.wallet.Wallet;
import com.team.shared.engine.data.user.wallet.operation.Operation;
import com.team.shared.engine.data.user.wallet.operation.type.OperationType;
import com.team.shared.engine.engine.Engine;
import com.team.shared.engine.timestamp.TimeStamp;
import com.team.shared.model.notification.Notification;
import com.team.shared.model.notification.type.NotificationType;
import com.team.web.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@code Service} which serves for handling {@link User} performances to the
 * <i>database</i> (Which on this project, stored in {@link
 * Engine#getUsers()}).
 * <p>
 * This service is ready for future use of <i>database</i> implementations.
 * </p>
 */
@Service public class UserServiceImpl implements UserService {

    /**
     * Creates a new {@link User} from the given {@link UserDTO}, and stores it
     * in the {@link User} {@link java.util.Collection} inside the {@link
     * Engine}.
     *
     * @param userDTO the {@link UserDTO} to create a new {@code User} from.
     * @return the response {@link UserDTO} extracted from the newly created
     * {@link User}.
     */
    @Override public UserDTO createUser(UserDTO userDTO) {

        // Set the role to upper-case:
        userDTO.setRole(userDTO.getRole().toUpperCase());

        // Create a new User from the given 'userDTO':
        User user =
                new User(userDTO.getName(), Role.valueOf(userDTO.getRole()));

        /*
         * Check if the user is already exists, before inserting it to the
         * database.
         */
        if (Engine.findUserByNameForced(user.getName()) != null) {
            throw new RuntimeException("User Already Exists"); // error 500
        }

        // Add the new User to the Engine's database of Users:
        Engine.insertUserForced(user);

        // Create a response userDTO from the newly made User:
        UserDTO returnValue = new UserDTO();
        returnValue.setName(user.getName());
        return returnValue;
    }

    /**
     * <ul>
     *     <li>Finds the according {@link User} <i>found by name</i> of this
     *     {@code userName}.
     *     <li>Inserts the found {@link User} to the
     *     {@link Engine#getSignedInUsers()} list.</li>
     * </ul>
     *
     * @param userName the {@link User#getName()} of the {@link User} to insert
     *                 to the {@link Engine#getSignedInUsers()}.
     */
    @Override public void insertToSignedInUsersList(String userName) {

        // Check if the signedInUsersList already contains this username:
        for (UserDTO userDTO : Engine.getSignedInUsers()) {
            if (userDTO.getName().equalsIgnoreCase(userName)) {
                /*
                 * User is already signed-in, So do not add it to the
                 * signedInUsersList, and quit.
                 */
                return;
            }
        }

        // Get the User:
        User user =
                Objects.requireNonNull(Engine.findUserByNameForced(userName));

        // Set the requestUserDTO with its found Role.
        UserDTO userDTO = new UserDTO(userName, user.getRole().toString());

        // Add the UserDTO to the Engine's signedInUsers List:
        Engine.getSignedInUsers().add(userDTO);
    }

    @Override public User addBalance(WalletBalanceDTO walletBalanceDTO) {
        User user = Engine.findUserByNameForced(walletBalanceDTO.getUserName());
        Wallet userWallet = user.getWallet();

        long balanceTransferred = walletBalanceDTO.getWalletBalanceToAdd();
        long balanceBefore = userWallet.getBalance();
        long balanceAfter = balanceBefore + balanceTransferred;

        // Create new Operation:
        userWallet.getOperationsList().addFirst(
                new Operation(OperationType.CHARGE, TimeStamp.getTimeStamp(),
                        balanceTransferred, balanceBefore, balanceAfter));

        // Update new balance:
        userWallet.setBalance(balanceAfter);

        return user;
    }

    /**
     * Validates the <i>request</i> of adding a company, and adds it in case of
     * success validation. The validation may fail in case there is already a
     * {@link Stock} with that {@code Symbol} in the {@link
     * Engine#getStocks()}.
     *
     * @param companyDTO {@code DTO} for the {@link com.team.shared.engine.data.stock.Stock}
     *                   to calculate its worth, to add to the {@link User}, and
     *                   to the {@link Engine#getStocks()} <i>database</i>.
     * @return In case of <b>success</b>, returns {@link Optional} of the
     * <i>requesting</i> {@link User}. In case of <b>fail</b>, returns
     * {@link Optional} of {@code null}.
     */
    @Override public Optional<User> addCompany(CompanyDTO companyDTO) {
        Optional<User> optionalUser = Optional.empty();
        long stockPrice = companyDTO.getWorth() / companyDTO.getQuantity();
        Stock stock =
                new Stock(companyDTO.getSymbol(), companyDTO.getCompanyName(),
                        stockPrice);
        if (!isStockSymbolInEngineAlready(stock)) {

            // Add the stock to the Engine:
            Engine.getStocksForced().getCollection().add(stock);

            // Add the stock to the requesting User's holdings:
            User user = Engine.findUserByNameForced(companyDTO.getUserName());
            user.getHoldings().getCollection()
                    .add(new Item(stock.getSymbol(), companyDTO.getQuantity()));

            // Notify all users about the success:
            notifySuccessOfAddCompany(user.getName(), stock);

            // Update return value of the success uploading user:
            optionalUser = Optional.of(user);
        } else {

            // Notify only this uploading user about the error:
            notifyErrorOfAddCompany(companyDTO.getUserName());
        }
        return optionalUser;
    }

    private void notifySuccessOfAddCompany(String uploadingUserName,
                                           Stock stock) {

        // Notify all users that there was a successful upload:
        Engine.getUsersForced().getCollection().forEach(user -> {
            user.getNotifications().addNotification(
                    new Notification(NotificationType.SUCCESS,
                            "Notify All Users: Success on adding a company: " +
                                    stock.getCompanyName(),
                            "A new company has been added: " + " (by" + " " +
                                    uploadingUserName + ")" + ": " + stock));
        });
    }

    private void notifyErrorOfAddCompany(String uploadingUserName) {

        // Notify uploading user.
        Engine.findUserByNameForced(uploadingUserName).getNotifications()
                .addNotification(new Notification(NotificationType.ERROR,
                        "Error while adding a company",
                        "There is already an existing company with the given " +
                                "'Symbol'"));
    }

    private boolean isStockSymbolInEngineAlready(Stock stockToValidate) {
        boolean isStockInEngineAlready = false;
        List<Stock> stocksInEngine = Engine.getStocksForced().getCollection();
        for (Stock stockInEngine : stocksInEngine) {
            if (stockInEngine.getSymbol()
                    .equalsIgnoreCase(stockToValidate.getSymbol())) {
                isStockInEngineAlready = true;
                break;
            }
        }
        return isStockInEngineAlready;
    }

}
