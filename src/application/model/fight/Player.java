package application.model.fight;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import application.model.appmodel.TeamBuilder;
import application.model.items.Item;
import application.model.moves.AttackResult;
import application.model.moves.Move;
import application.model.pokemon.Pokemon;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Player implements Serializable{
	private final ArrayList<Pokemon> team;
	private Pokemon selectedPokemon;
	private int alive;
	private final boolean bot;
	private Action nextAction;
	private int whichAction;
	private Player whichPlayer;
	private ArrayList<Item> backPack = new ArrayList<>();

	public Player(ArrayList<Pokemon> team, boolean bot) {
		this.team = Objects.requireNonNull(team);
		this.selectedPokemon = team.get(0); // Le premier Pokémon est celui lancé en premier
		alive = team.size();
		this.bot = bot;
	}
	
	public Player(TeamBuilder teamBuilder, boolean bot) {
		this(teamBuilder.getTeam(), bot);
	}

	public static Player createRandomPlayer(boolean b) throws IOException {
		return new Player(TeamBuilder.createTeamBuilder().createRandomTeam(), b);
	}

	public void switchPokemon(Pokemon p) {
		if (p.isAlive()) {
			selectedPokemon = p;
		} else {
			throw new IllegalArgumentException("The pokemon is KO");
		}
	}

	public boolean isBot() {
		return bot;
	}
	
	public void setNextAction(Action nextAction, int which,Player target) {
		this.nextAction = nextAction;
		this.whichAction = which;
		whichPlayer = target;
	}
	
	public Pokemon getSelectedPokemon() {
		return selectedPokemon;
	}
	
	public Action getNextAction() {
		return nextAction;
	}
	
	/*
	 * On dit :
	 * 80% de chance que le bot attaque avec un attaque aléatoire
	 * 20 % il switch sur un pokémon aléatoire
	 */
	public void generateNextAction(Player p) {
		double choice = Math.random();
		Random randomChoice = new Random();
		if(choice < 0.80) {
			System.out.println("Le bot attaquera !");
			setNextAction(Action.MOVE, randomChoice.nextInt(selectedPokemon.getlearnedMoves().size()),p);
		}else {
			System.out.println("Le bot changera de pokémon ! "+team.size());
			int next;
			do {
				next =  randomChoice.nextInt(team.size());
			} while (!team.get(next).isAlive() && !team.get(next).equals(selectedPokemon));
			setNextAction(Action.SWITCH,next ,p);
		}
		
	}
	
	public void forceSwitch() {
		Random randomChoice = new Random();
		int next;
		do {
			next =  randomChoice.nextInt(team.size());
		} while (!team.get(next).isAlive());
		setNextAction(Action.SWITCH,next ,whichPlayer);
	}
	
	
	/*
	 * Méthode principale : Effectue le tour du joueur
	 */
	public AttackResult turn(Player p) {
		switch(nextAction) {
			case MOVE:
				return selectedPokemon.getlearnedMoves().get(whichAction).use(selectedPokemon, p.selectedPokemon);
			case SWITCH:
				switchPokemon(team.get(whichAction));
			default:
				break;
		}
			
		return null;
	}
	
	public int getWhichAction() {
		return whichAction;
	}
	
	public ArrayList<Pokemon> getTeam() {
		return team;
	}
	
	/*
	 * Check différents paramètres:
	 * 	-Le pokémon actuel est il vivant ?
	 */
	public boolean isEverythingOk() {
		if(!selectedPokemon.isAlive()) {
			alive -=1;
			return false;
		};
		return true;
	}
	
	public Player getWhichPlayer() {
		return whichPlayer;
	}
}
