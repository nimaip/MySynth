package Music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;

public class Synth {

	private static List<String> notes = Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B",
			"C");
	private static MidiChannel[] channels;
	private static int INSTRUMENT = 0;
	private static int VOLUME = 100;

	// Main method

	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();

			// Created methods:

//			playChromatic("C");
//			playMajorScale("F", 400);
//			playMinorScale("C", 400);
//			playRand(20);
//			playRandScale(30);
//			playInput();
//			circleOfFifths();
//			playAllScales();
//			transposeNote("5C", "C", "G");
			transposeSong();

			synth.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void transposeSong() {
		String str = "4F# 4G# 4A 4B 4A 4G# 4A 4G# 4F#";
		String[] notes = str.split(" ");
		try {
			for (String s : notes) {
				System.out.print(s + "\t");
				play(s, 700);
			}

			rest(1000);

			System.out.println("\n\nNew Notes:\n");
			for (String s : notes) {
				String t = transposeNote(s, "F#", "G#");
				System.out.print(t + "\t");
				play(t, 700);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String transposeNote(String note, String originalScale, String newScale) {
		int noteIndex = notes.indexOf(note.substring(1));
		int difference = (noteIndex + 12 - notes.indexOf(originalScale)) % 12;
		int finNote = notes.indexOf(newScale) + difference;

		int octave = Integer.parseInt(note.substring(0, 1));
		if (finNote >= 12)
			octave++;

		return octave + notes.get(finNote % 12);
	}

	public static void playChromatic(String first) {
		try {
			int n = notes.indexOf(first);
			for (int i = 0; i < 14; i++) {
				if (n < 13)
					play("5" + first, 1000);
				else
					play("6" + first, 1000);
				first = notes.get(n++ % 12);
			}
		} catch (Exception e) {
		}
	}

	public static void playMajorScale(String first, int time) {
		try {
			int n = notes.indexOf(first);
			String octave = "5";
			for (int i = 0; i < 8; i++) {
				if (n >= 12)
					octave = "6";
				else
					octave = "5";
				play(octave + notes.get(n % 12), time);
				if (i == 2 || i == 6)
					n++;
				else
					n += 2;
			}
			n = notes.indexOf(first) + 11;
			for (int i = 1; i < 8; i++) {
				if (n >= 12)
					octave = "6";
				else
					octave = "5";
				// Change Speed
				if (i == 7)
					play(octave + notes.get(n % 12), time / 2 * 3);
				else if (i == 6)
					play(octave + notes.get(n % 12), time / 3);
				else
					play(octave + notes.get(n % 12), time);

				// Spin & Intervals
				if (i == 4)
					n--;
				else if (i == 5) {
					if (n - 2 >= 12)
						octave = "6";
					else
						octave = "5";
					play(octave + notes.get(n - 2), time / 4);
					if (n >= 12)
						octave = "6";
					else
						octave = "5";
					play(octave + notes.get(n), time / 4);
					n -= 2;
				} else
					n -= 2;

			}
		} catch (Exception e) {
		}

	}

	public static void playMinorScale(String first, int time) {
		try {
			int n = notes.indexOf(first);
			String octave = "5";
			for (int i = 0; i < 8; i++) {
				play(octave + notes.get(n % 12), time);
				if (i == 1 || i == 4)
					n++;
				else
					n += 2;
				if (n >= 12)
					octave = "6";
			}
			n = notes.indexOf(first) + 10;
			for (int i = 1; i < 8; i++) {
				if (n >= 12)
					octave = "6";
				else
					octave = "5";

				// Change Speed
				if (i == 7)
					play(octave + notes.get(n % 12), time / 2 * 3);
				else if (i == 6)
					play(octave + notes.get(n % 12), time / 3);
				else
					play(octave + notes.get(n % 12), time);

				// Intervals
				if (i == 2)
					n--;
				else if (i == 5) {
					if (n - 2 >= 12)
						octave = "6";
					else
						octave = "5";
					play(octave + notes.get(n - 1), time / 4);
					if (n >= 12)
						octave = "6";
					else
						octave = "5";
					play(octave + notes.get(n), time / 4);
					n--;
				} else
					n -= 2;
			}
		} catch (Exception e) {
		}
	}

	public static void playRand(int count) {
		try {
			for (int i = 0; i < count; i++) {
				int time = (int) (Math.random() * 600 + 50);
				play(getNote(), time);
			}

			rest(1000);
		} catch (Exception e) {
		}
	}

	public static void playRandScale(int count) {
		try {
			int[] nums = { 0, 2, 4, 5, 7, 9, 11, 12 };
			for (int i = 0; i < count - 1; i++) {
				int time = (int) (Math.random() * 900 + 100);
				int n = (int) (Math.random() * nums.length);
				if (n == 12)
					play("6C", time);
				play("5" + notes.get(nums[n]), time);
			}
			play("6" + nums[nums.length - 1], 3000);
			rest(1000);

		} catch (Exception e) {
		}
	}

	public static void playInput() {
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Enter some notes:");
			for (int i = 0; i < 100; i++) {
				String let = in.next();
				play(getLet(let), 200);
			}

		} catch (Exception e) {
		}
	}

	public static void circleOfFifths() {
		try {
			int curr = 0;
			System.out.println(notes.get(curr));
			play("5" + notes.get(curr), 1000);
			for (int i = 0; i < 12; i++) {
				curr = curr + 7;
				curr %= 12;
				play("5" + notes.get(curr), 1000);
				System.out.println(notes.get(curr));
			}

		} catch (Exception e) {
		}
	}

	public static void playAllScales() {
		try {

			for (int i = 0; i < notes.size() - 1; i++) {
				System.out.println("");
				String s = notes.get(i);
				playMajorScale(s, 400);
				rest(1000);
				playMinorScale(s, 400);
				rest(1000);
			}
		} catch (Exception e) {
		}
	}

	public static String getLet(String n) {
		switch (n) {
		case "a":
			return "5C";
		case "w":
			return "5C#";
		case "s":
			return "5D";
		case "e":
			return "5D#";
		case "d":
			return "5E";
		case "f":
			return "5F";
		case "t":
			return "5F#";
		case "g":
			return "5G";
		case "y":
			return "5G#";
		case "h":
			return "5A";
		case "u":
			return "5A#";
		case "j":
			return "5B";
		case "k":
			return "6C";
		}
		return "6C";
	}

	public static String getNote() {
		int num = (int) (Math.random() * 13);
		return "5" + notes.get(num);
	}

//Prewritten methods

	private static void play(String note, int duration) throws InterruptedException {
		channels[INSTRUMENT].noteOn(id(note), VOLUME);
		Thread.sleep(duration);
		channels[INSTRUMENT].noteOff(id(note));
	}

	private static void rest(int duration) throws InterruptedException {
		Thread.sleep(duration);
	}

	private static int id(String note) {
		int octave = Integer.parseInt(note.substring(0, 1));
		return notes.indexOf(note.substring(1)) + 12 * octave + 12;
	}
}