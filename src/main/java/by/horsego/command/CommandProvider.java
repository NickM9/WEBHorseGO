package by.horsego.command;

import by.horsego.command.impl.bet_commands.*;
import by.horsego.command.impl.game_commands.*;
import by.horsego.command.impl.horse_commands.*;
import by.horsego.command.impl.user_commands.*;
import org.apache.log4j.Logger;

/**
 * Provider with command enums, which create the command object.
 * Class has a static method getCommand {@link #getCommand(String)}
 * which converts a string to uppercase and returns a command object.
 *
 * @see #getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public enum CommandProvider {

    // user
    LOG_IN(new LogInCommand()),
    SIGN_UP (new SignUpCommand()),
    LOG_OUT(new LogOutCommand()),
    SHOW_START_TABLES(new ShowStartTablesCommand()),
    INCREASE_WALLET(new IncreaseWalletCommand()),
    DECREASE_WALLET(new DecreaseWalletCommand()),
    CREATE_BET(new CreateBetCommand()),
    SHOW_BETS_HISTORY(new ShowBetsHistoryCommand()),
    SHOW_GAMES(new ShowGamesCommand()),
    SHOW_GAMES_HISTORY(new ShowGamesHistoryCommand()),
    FIND_GAME_BY_ID(new FindGameByIdCommand()),
    SHOW_HORSES(new ShowHorsesCommand()),
    FIND_HORSE_BY_ID(new FindHorseByIdCommand()),
    UPDATE_LOGIN(new UpdateLoginCommand()),
    UPDATE_PASSWORD(new UpdatePasswordCommand()),
    UPDATE_NAME(new UpdateNameCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),

    // admin
    ADMIN_CREATE_GAME(new AdminCreateGameCommand()),
    ADMIN_UPDATE_GAME(new AdminUpdateGameCommand()),
    ADMIN_DELETE_GAME(new AdminDeleteGameCommand()),
    ADMIN_START_GAME(new AdminStartGameCommand()),
    ADMIN_CREATE_HORSE(new AdminCreateHorseCommand()),
    ADMIN_UPDATE_HORSE(new AdminUpdateHorseCommand()),
    ADMIN_DELETE_HORSE(new AdminDeleteHorseCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    FIND_USER_BY_ID(new FindUserByIdCommand()),
    FIND_USER_BY_LOGIN(new FindUserByLoginCommand()),
    FIND_USER_BY_NAME_AND_SURNAME(new FindUserByNameAndSurnameCommand()),
    ADMIN_CREATE_USER(new AdminCreateUserCommand()),
    ADMIN_UPDATE_USER(new AdminUpdateUserCommand()),
    ADMIN_DELETE_USER(new AdminDeleteUserCommand()),

    // bookmaker
    BOOKMAKER_UPDATE_GAME_BETS(new BookmakerUpdateGameBetsCommand());


    private Command command;
    private static final Logger logger = Logger.getLogger(CommandProvider.class);

    CommandProvider(Command command){
        this.command = command;
    }

    public static Command getCommand(String stringCommand) throws CommandException {
        stringCommand = stringCommand.toUpperCase();
        logger.info(stringCommand);

        Command command;
        try {
            command = CommandProvider.valueOf(stringCommand).command;
        } catch (IllegalArgumentException | NullPointerException e){
            logger.warn(e);
            throw new CommandException(e);
        }

        return command;
    }


}
