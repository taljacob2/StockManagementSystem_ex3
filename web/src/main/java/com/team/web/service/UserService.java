package com.team.web.service;

import com.team.shared.dto.CompanyDTO;
import com.team.shared.dto.UserDTO;
import com.team.shared.dto.WalletBalanceDTO;
import com.team.shared.engine.data.user.User;
import com.team.shared.engine.data.user.wallet.Wallet;
import com.team.shared.engine.engine.Engine;
import com.team.web.service.impl.UserServiceImpl;

/**
 * An <i>interface</i> that contains other methods for the {@code Service} of
 * handling {@link User} transfers through <i>database</i>.
 *
 * @see UserServiceImpl
 */
public interface UserService {

    /**
     * This method creates a new {@code User} from the given {@code UserDTO}.
     *
     * @param userDTO the {@link UserDTO} to create a new {@code User} from.
     * @return the {@link UserDTO} of the newly created {@code User}.
     */
    UserDTO createUser(UserDTO userDTO);

    /**
     * This method inserts a new {@link UserDTO} to the {@link
     * Engine#getSignedInUsers()}.
     *
     * @param userName of the <i>uploading</i> {@link User}.
     */
    void insertToSignedInUsersList(String userName);

    /**
     * Adds {@link Wallet#getBalance()} to a {@link Wallet} of a {@link User}.
     *
     * @param walletBalanceDTO {@code DTO} for the {@link Wallet#getBalance()}
     *                         to add to a {@link User}.
     * @return the {@link User} we added balance to its {@link Wallet}.
     */
    User addBalance(WalletBalanceDTO walletBalanceDTO);

    /**
     * Adds a <i>new</i> company {@link com.team.shared.engine.data.stock.Stock}
     * to the {@link com.team.shared.engine.data.user.holding.Holdings} of a
     * <i>uploading</i> {@link User}, while uploading its existence to the
     * {@link Engine#getStocks()} <i>database</i>.
     *
     * @param companyDTO {@code DTO} for the {@link com.team.shared.engine.data.stock.Stock}
     *                   to calculate its worth, to add to the {@link User}, and
     *                   to the {@link Engine#getStocks()} <i>database</i>.
     * @return the <i>uploading</i> {@link User} we added the {@link
     * com.team.shared.engine.data.stock.Stock} to its {@link
     * com.team.shared.engine.data.user.holding.Holdings}.
     */
    User addCompany(CompanyDTO companyDTO);

}
