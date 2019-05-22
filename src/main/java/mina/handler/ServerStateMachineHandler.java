package mina.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.statemachine.annotation.IoHandlerTransition;
import org.apache.mina.statemachine.annotation.IoHandlerTransitions;
import org.apache.mina.statemachine.annotation.State;
import org.apache.mina.statemachine.context.AbstractStateContext;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.apache.mina.statemachine.event.IoHandlerEvents.EXCEPTION_CAUGHT;
import static org.apache.mina.statemachine.event.IoHandlerEvents.MESSAGE_RECEIVED;
import static org.apache.mina.statemachine.event.IoHandlerEvents.SESSION_CLOSED;
import static org.apache.mina.statemachine.event.IoHandlerEvents.SESSION_OPENED;

/**
 * 状态机消息处理handler
 */
public class ServerStateMachineHandler
{
	@State
	private static final String ROOT = "Root";

	@State(ROOT)
	public static final String NOT_CONNECTED = "NotConnected";

	@State(ROOT)
	private static final String IDLE = "Idle";

	@State(ROOT)
	private static final String INVITATION_SENT = "InvitationSent";

	@State(ROOT)
	private static final String INVITATION_ACCEPTED = "InvitationAccepted";

	@State(ROOT)
	private static final String PLAYING = "Playing";

	private final Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());

	private final Set<String> users = Collections.synchronizedSet(new HashSet<String>());

	//private final PlayingGames playingGames = new PlayingGames();

	public static class TetrisServerContext extends AbstractStateContext
	{
		public String nickName;
	}

	@IoHandlerTransition(on = SESSION_OPENED, in = NOT_CONNECTED)
	public void connect(IoSession session)
	{

	}

//	@IoHandlerTransition(on = MESSAGE_RECEIVED, in = NOT_CONNECTED, next = IDLE)
//	public void login(TetrisServerContext context, IoSession session,
//			LoginCommand cmd) {
//		String nickName = cmd.getNickName();
//		context.nickName = nickName;
//		session.setAttribute("nickname", nickName);
//		session.setAttribute("status", UserStatus.IDLE);
//		sessions.add(session);
//		users.add(nickName);
//
//		RefreshPlayersListCommand command = createRefreshPlayersListCommand();
//		broadcast(command);
//	}
//
//	@IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = INVITATION_SENT)
//	public void invite(TetrisServerContext context, IoSession session,
//			InviteCommand cmd) {
//		String inviteeName = cmd.getInviteeName();
//		InvitationReceivedCommand irc = new InvitationReceivedCommand();
//		String currentUser = (String) session.getAttribute("nickname");
//		irc.setInviterName(currentUser);
//		sendCommandToUser(inviteeName, irc);
//	}
//
//	@IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = INVITATION_ACCEPTED)
//	public void acceptInvitation(TetrisServerContext context,
//                                 IoSession session, AcceptInvitationCommand cmd) {
//		String inviterName = cmd.getInviterName();
//		String currentUser = (String) session.getAttribute("nickname");
//		StartGameCommand commandToInviter = new StartGameCommand(currentUser);
//		sendCommandToUser(inviterName, commandToInviter);
//		StartGameCommand commandToCurrentUser = new StartGameCommand(
//				inviterName);
//		sendCommandToCurrentUser(session, commandToCurrentUser);
//
//		playingGames.newGameStarted(currentUser, inviterName);
//	}
//
//	@IoHandlerTransitions( {
//			@IoHandlerTransition(on = MESSAGE_RECEIVED, in = INVITATION_SENT, next = PLAYING),
//			@IoHandlerTransition(on = MESSAGE_RECEIVED, in = INVITATION_ACCEPTED, next = PLAYING) })
//	public void gameStarted(TetrisServerContext context, IoSession session,
//			StartGameConfirmCommand cmd) {
//		session.setAttribute("status", UserStatus.PLAYING);
//	}
//
//	@IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAYING)
//	public void syncBoard(TetrisServerContext context, IoSession session,
//			SyncBoardCommand cmd) {
//		String currentUser = (String) session.getAttribute("nickname");
//		String opponent = playingGames.findOpponent(currentUser);
//		sendCommandToUser(opponent, cmd);
//	}
//
//	@IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAYING, next = IDLE)
//	public void gameWin(TetrisServerContext context, IoSession session, GameWinCommand cmd) {
//
//	}
//
//	@IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAYING, next = IDLE)
//	public void gameLost(TetrisServerContext context, IoSession session, GameLostCommand cmd) {
//		String currentUser = (String) session.getAttribute("nickname");
//		String opponent = playingGames.findOpponent(currentUser);
//		sendCommandToUser(opponent, new GameWinCommand());
//		playingGames.endGame(currentUser);
//	}
//
//	@IoHandlerTransition(on = EXCEPTION_CAUGHT, in = ROOT, weight = 10)
//	public void exceptionCaught(IoSession session, Exception e) {
//		LOGGER.warn("Unexpected error.", e);
//		session.close(true);
//	}
//
//	@IoHandlerTransition(on = SESSION_CLOSED, in = ROOT)
//	public void logout(TetrisServerContext context, IoSession session) {
//		String user = context.nickName;
//		users.remove(user);
//		sessions.remove(session);
//	}
//
//	public void messageReceived(IoSession session, Object message)
//			throws Exception {
//		if (!(message instanceof AbstractTetrisCommand)) {
//			LOGGER.debug("Unexpected message, drop it.");
//		}
//
//	}
//
//	@IoHandlerTransition(in = ROOT, weight = 100)
//	public void unhandledEvent() {
//		LOGGER.warn("Unhandled event.");
//	}
//
//	private void sendCommandToUser(String nickname,
//			AbstractTetrisCommand command) {
//		synchronized (sessions) {
//			for (IoSession session : sessions) {
//				if (nickname.equals(session.getAttribute("nickname"))) {
//					session.write(command);
//					break;
//				}
//			}
//		}
//	}
//
//	private void sendCommandToCurrentUser(IoSession session,
//			AbstractTetrisCommand command) {
//		session.write(command);
//	}
//
//	private void broadcast(AbstractTetrisCommand command) {
//		synchronized (sessions) {
//			for (IoSession session : sessions) {
//				if (session.isConnected()) {
//					session.write(command);
//				}
//			}
//		}
//	}
//
//	private RefreshPlayersListCommand createRefreshPlayersListCommand() {
//		RefreshPlayersListCommand command = new RefreshPlayersListCommand();
//		synchronized (sessions) {
//			for (IoSession session : sessions) {
//				if (session.isConnected()) {
//					String nickname = (String) session.getAttribute("nickname");
//					UserStatus status = (UserStatus) session
//							.getAttribute("status");
//					command.addPlayer(nickname, status);
//				}
//			}
//		}
//		return command;
//	}
}
