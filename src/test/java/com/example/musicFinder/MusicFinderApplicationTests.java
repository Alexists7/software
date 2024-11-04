package com.example.musicFinder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLOutput;

@SpringBootTest
class MusicFinderApplicationTests {


		@Autowired
		private MusicFinderController musicFinderController;

		@Mock
		private MusicFinderController musicFinderControllerMock;

		@Test
		void contextLoads() throws Exception{
			assertThat(musicFinderController).isNotNull();
		}

		@Test
		public void testFetchLyrics_ValidSong() {
			// Add code to test valid artist/song request
			// Arrange: Define test input and expected output
			// Initialize mocks
			MockitoAnnotations.openMocks(this);

			// Arrange: Define test input and expected output (only the first line)
			String artist = "Adele";
			String song = "Hello";
			String expectedLyrics = "Hello, it's me"; // Only the first line of lyrics

			// Act: Call getSongDetails
			ObjectNode response = musicFinderController.getSongDetails(artist, song);

			// Extract the first line from the lyrics in the response for assertion
			String actualLyrics = response.get("lyrics").asText().split("<br>")[0];

			// Assert: Verify response content
			assertThat(response.get("song").asText()).isEqualTo(song);
			assertThat(response.get("artist").asText()).isEqualTo(artist);
			assertThat(actualLyrics).isEqualTo(expectedLyrics);
			System.out.println("200 OK");
		}

		@Test
		public void testFetchLyrics_InvalidSong() {
			// Add code to test invalid artist/song request
			// and error handling as well
			// Initialize mocks
			MockitoAnnotations.openMocks(this);

			// Arrange: Define test input for invalid artist and song
			String invalidArtist = "Adele";
			String invalidSong = "Bye Bye Bye";
			String expectedError = "{\"error\":\"Lyrics not found\"}";

			try {
				// Act: Call getSongDetails with invalid artist and song
				ObjectNode response = musicFinderController.getSongDetails(invalidArtist, invalidSong);

				// Assert: Verify that the response contains the expected error message in the lyrics field
				assertThat(response.get("lyrics").asText()).isEqualTo(expectedError);
				System.out.println("404 Not Found");
			} catch (Exception e) {
				// If an exception occurs, fail the test and log the error
				fail("Exception occurred during testFetchLyrics_InvalidSong: " + e.getMessage());
			}
		}
}


