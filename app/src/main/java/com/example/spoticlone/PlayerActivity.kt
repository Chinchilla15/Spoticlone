package com.example.spoticlone

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {

    private var isPlaying = false
    private var isFavorite = false
    private var isShuffleOn = false
    private var repeatMode = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player)

        val backButton = findViewById<Button>(R.id.backButton)
        val moreOptionsButton = findViewById<Button>(R.id.moreOptionsButton)
        val favoriteButton = findViewById<Button>(R.id.favoriteButton)
        val playPauseButton = findViewById<Button>(R.id.playPauseButton)
        val previousButton = findViewById<Button>(R.id.previousButton)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val shuffleButton = findViewById<Button>(R.id.shuffleButton)
        val repeatButton = findViewById<Button>(R.id.repeatButton)
        val queueButton = findViewById<Button>(R.id.queueButton)
        val volumeButton = findViewById<Button>(R.id.volumeButton)
        val shareButton = findViewById<Button>(R.id.shareButton)

        val progressBar = findViewById<SeekBar>(R.id.progressBar)
        val volumeSeekBar = findViewById<SeekBar>(R.id.volumeSeekBar)

        val songTitle = findViewById<TextView>(R.id.songTitle)
        val artistName = findViewById<TextView>(R.id.artistName)
        val currentTime = findViewById<TextView>(R.id.currentTime)
        val totalTime = findViewById<TextView>(R.id.totalTime)

        backButton.setOnClickListener {
            finish()
        }


        playPauseButton.setOnClickListener {
            togglePlayPause(playPauseButton)
        }

        favoriteButton.setOnClickListener {
            toggleFavorite(favoriteButton)
        }

        shuffleButton.setOnClickListener {
            toggleShuffle(shuffleButton)
        }

        repeatButton.setOnClickListener {
            toggleRepeat(repeatButton)
        }

        previousButton.setOnClickListener {
            Toast.makeText(this, "Canción anterior", Toast.LENGTH_SHORT).show()
            updateSongInfo(songTitle, artistName, "Hotel California", "Eagles")
            resetProgress()
        }

        nextButton.setOnClickListener {
            Toast.makeText(this, "Siguiente canción", Toast.LENGTH_SHORT).show()
            updateSongInfo(songTitle, artistName, "Stairway to Heaven", "Led Zeppelin")
            resetProgress()
        }

        progressBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val totalSeconds = (progress * 225) / 100
                    val minutes = totalSeconds / 60
                    val seconds = totalSeconds % 60
                    currentTime.text = String.format("%d:%02d", minutes, seconds)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(this@PlayerActivity, "Posición actualizada", Toast.LENGTH_SHORT).show()
            }
        })

        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val volume = volumeSeekBar.progress
                Toast.makeText(this@PlayerActivity, "Volumen: $volume%", Toast.LENGTH_SHORT).show()
            }
        })

        moreOptionsButton.setOnClickListener {
            Toast.makeText(this, "Más opciones", Toast.LENGTH_SHORT).show()
        }

        queueButton.setOnClickListener {
            Toast.makeText(this, "Cola de reproducción", Toast.LENGTH_SHORT).show()
        }

        volumeButton.setOnClickListener {
            val currentVolume = volumeSeekBar.progress
            if (currentVolume > 0) {
                volumeSeekBar.progress = 0
                volumeButton.text = "🔇"
                Toast.makeText(this, "Silenciado", Toast.LENGTH_SHORT).show()
            } else {
                volumeSeekBar.progress = 75
                volumeButton.text = "🔊"
                Toast.makeText(this, "Volumen restaurado", Toast.LENGTH_SHORT).show()
            }
        }

        shareButton.setOnClickListener {
            Toast.makeText(this, "Compartir: ${songTitle.text} - ${artistName.text}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun togglePlayPause(button: Button) {
        isPlaying = !isPlaying
        if (isPlaying) {
            button.text = "⏸"
            Toast.makeText(this, "Reproduciendo", Toast.LENGTH_SHORT).show()
        } else {
            button.text = "▶"
            Toast.makeText(this, "Pausado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleFavorite(button: Button) {
        isFavorite = !isFavorite
        if (isFavorite) {
            button.text = "♥"
            button.setTextColor(resources.getColor(android.R.color.holo_green_light, theme))
            Toast.makeText(this, "Agregado a favoritos", Toast.LENGTH_SHORT).show()
        } else {
            button.text = "♡"
            button.setTextColor(resources.getColor(android.R.color.darker_gray, theme))
            Toast.makeText(this, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleShuffle(button: Button) {
        isShuffleOn = !isShuffleOn
        if (isShuffleOn) {
            button.setTextColor(resources.getColor(android.R.color.holo_green_light, theme))
            Toast.makeText(this, "Aleatorio activado", Toast.LENGTH_SHORT).show()
        } else {
            button.setTextColor(resources.getColor(android.R.color.darker_gray, theme))
            Toast.makeText(this, "Aleatorio desactivado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleRepeat(button: Button) {
        repeatMode = (repeatMode + 1) % 3
        when (repeatMode) {
            0 -> {
                button.text = "🔁"
                button.setTextColor(resources.getColor(android.R.color.darker_gray, theme))
                Toast.makeText(this, "Repetir desactivado", Toast.LENGTH_SHORT).show()
            }
            1 -> {
                button.text = "🔁"
                button.setTextColor(resources.getColor(android.R.color.holo_green_light, theme))
                Toast.makeText(this, "Repetir todo", Toast.LENGTH_SHORT).show()
            }
            2 -> {
                button.text = "🔂"
                button.setTextColor(resources.getColor(android.R.color.holo_green_light, theme))
                Toast.makeText(this, "Repetir una", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSongInfo(titleView: TextView, artistView: TextView, title: String, artist: String) {
        titleView.text = title
        artistView.text = artist
    }

    private fun resetProgress() {
        findViewById<SeekBar>(R.id.progressBar).progress = 0
        findViewById<TextView>(R.id.currentTime).text = "0:00"
    }
}