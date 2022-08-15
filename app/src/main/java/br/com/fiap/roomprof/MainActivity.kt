package br.com.fiap.roomprof

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import br.com.fiap.roomprof.model.Game
import br.com.fiap.roomprof.repository.GameRepository
import br.com.fiap.roomprof.utils.toBitmap
import br.com.fiap.roomprof.utils.toByteArray

class MainActivity : AppCompatActivity() {

    lateinit var btnSalvar: Button
    lateinit var btnListarTodos: Button
    lateinit var btnUpdate: Button
    lateinit var btnDelete: Button
    lateinit var btnFinalizados: Button
    lateinit var btnBuscaId: Button
    lateinit var editId: EditText

    var imageBitmap: Bitmap? = null

    lateinit var btnGaleria: Button
    lateinit var btnCamera: Button
    lateinit var imageGaleria: ImageView

    val galeria = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val bitmap = it?.data?.extras?.get("data") as Bitmap
        imageGaleria.setImageBitmap(bitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && requestCode == 200) {
            val inputStream = contentResolver.openInputStream(data.data!!)
            imageBitmap = BitmapFactory.decodeStream(inputStream)
            imageGaleria.setImageBitmap(imageBitmap)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSalvar = findViewById(R.id.btn_salvar)
        btnListarTodos = findViewById(R.id.btn_listar_todos)
        btnUpdate = findViewById(R.id.btn_update)
        btnDelete = findViewById(R.id.btn_delete)
        btnFinalizados = findViewById(R.id.btn_finalizados)
        btnBuscaId = findViewById(R.id.btn_busca_id)

        // Fotos
        btnGaleria = findViewById(R.id.button_image_galeria)
        btnCamera = findViewById(R.id.button_image_camera)
        imageGaleria = findViewById(R.id.image_galeria)

        btnGaleria.setOnClickListener {
            val intentGaleria = Intent(Intent.ACTION_GET_CONTENT)
            intentGaleria.setType("image/*")
            startActivityForResult(intentGaleria, 200)
            //galeria.launch(intentGaleria)

        }

        btnCamera.setOnClickListener {
            val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //startActivityForResult(intentCamera, 200)
            galeria.launch(intentCamera)
        }

        editId = findViewById(R.id.edit_id)

        val gr = GameRepository(this)

        btnSalvar.setOnClickListener {

            val game = Game()
            game.apply {
                estudio = "Data East"
                titulo = "Joe e Mac"
                valorEstimado = 450.0
                finalizado = false
                foto = imageBitmap!!.toByteArray()
            }

            val novoGame = gr.save(game)
            Log.i("xpto", novoGame.toString())
            Log.i("xpto", "----------------------------------------------------------")

        }

        btnListarTodos.setOnClickListener {
            val games = gr.getAllGames()
            for (game in games) {
                Log.i("xpto", "${game.id} - ${game.estudio} - ${game.titulo} - ${game.finalizado}")
            }
            Log.i("xpto", "----------------------------------------------------------")
        }

        btnUpdate.setOnClickListener {
            val game = Game()
            game.apply {
                id = 3
                estudio = "Ea-Sports"
                titulo = "Football"
                valorEstimado = 200.0
                finalizado = true
            }
            gr.update(game)
        }

        btnDelete.setOnClickListener {
            val id = editId.text.toString().toInt()
            val game = gr.getGameById(id)

            if (game != null){
                gr.delete(game)
                Log.i("xpto", "JOGO EXCLUÍDO COM SUCESSO!!")

            } else {
                Log.i("xpto", "JOGO NÃO ENCONTRADO COM ID $id")
                Log.i("xpto", "----------------------------------------------------------")
            }

        }

        btnFinalizados.setOnClickListener {
            val games = gr.getGamesFinished()
            for (game in games) {
                Log.i("xpto", "${game.id} - ${game.estudio} - ${game.titulo}")
            }
            Log.i("xpto", "----------------------------------------------------------")
        }

        btnBuscaId.setOnClickListener {
            val id = editId.text.toString().toInt()
            val game = gr.getGameById(id)
            imageGaleria.setImageBitmap(game.foto!!.toBitmap())

            if(game != null) {
                Log.i("xpto", "${game.id} - ${game.estudio} - ${game.titulo} - ${game.finalizado}")
                Log.i("xpto", "----------------------------------------------------------")
            } else {
                Log.i("xpto", "JOGO NÃO ENCONTRADO COM ID $id")
                Log.i("xpto", "----------------------------------------------------------")
            }
        }

    }
}