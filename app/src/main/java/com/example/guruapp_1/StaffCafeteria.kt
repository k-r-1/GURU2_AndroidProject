package com.example.guruapp_1

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream

class StaffCafeteria : AppCompatActivity() {
    private val resultLauncher: ActivityResultLauncher<Intent>? = null

    lateinit var imageView1: ImageView
    lateinit var imageView2: ImageView
    var imgName1 = "osz1.png" // 이미지 이름
    var imgName2 = "osz2.png" // 이미지 이름


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_cafeteria)

        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)

        try {
            val imgpath1 = "$cacheDir/$imgName1" // 내부 저장소에 저장되어 있는 이미지 경로
            val bm1 = BitmapFactory.decodeFile(imgpath1)
            imageView1.setImageBitmap(bm1) // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
            Toast.makeText(applicationContext, "파일 로드 성공", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "파일 로드 실패", Toast.LENGTH_SHORT).show()
        }

        try {
            val imgpath2 = "$cacheDir/$imgName2" // 내부 저장소에 저장되어 있는 이미지 경로
            val bm2 = BitmapFactory.decodeFile(imgpath2)
            imageView2.setImageBitmap(bm2) // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
            Toast.makeText(applicationContext, "파일 로드 성공", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "파일 로드 실패", Toast.LENGTH_SHORT).show()
        }
    }

    fun bt1(view1: View?) {    // 이미지 선택 누르면 실행됨 이미지 고를 갤러리 오픈
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        getResult1.launch(intent)
    }

    fun bt2(view2: View?) {    // 이미지 선택 누르면 실행됨 이미지 고를 갤러리 오픈
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        getResult2.launch(intent)
    }


    private val getResult1 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    val fileUri = result.data!!.data
                    val resolver = contentResolver
                    try {
                        val instream = resolver.openInputStream(fileUri!!)
                        val imgBitmap = BitmapFactory.decodeStream(instream)
                        imageView1!!.setImageBitmap(imgBitmap) // 선택한 이미지 이미지뷰에 셋
                        instream!!.close() // 스트림 닫아주기
                        saveBitmapToJpeg1(imgBitmap) // 내부 저장소에 저장
                        Toast.makeText(applicationContext, "파일 불러오기 성공", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "파일 불러오기 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private val getResult2 =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    val fileUri = result.data!!.data
                    val resolver = contentResolver
                    try {
                        val instream = resolver.openInputStream(fileUri!!)
                        val imgBitmap = BitmapFactory.decodeStream(instream)
                        imageView2!!.setImageBitmap(imgBitmap) // 선택한 이미지 이미지뷰에 셋
                        instream!!.close() // 스트림 닫아주기
                        saveBitmapToJpeg2(imgBitmap) // 내부 저장소에 저장
                        Toast.makeText(applicationContext, "파일 불러오기 성공", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "파일 불러오기 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    fun saveBitmapToJpeg1(bitmap: Bitmap) {   // 선택한 이미지 내부 저장소에 저장
        val tempFile = File(cacheDir, imgName1) // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile() // 자동으로 빈 파일을 생성하기
            val out = FileOutputStream(tempFile) // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close() // 스트림 닫아주기
            Toast.makeText(applicationContext, "파일 저장 성공", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "파일 저장 실패", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveBitmapToJpeg2(bitmap: Bitmap) {   // 선택한 이미지 내부 저장소에 저장
        val tempFile = File(cacheDir, imgName2) // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile() // 자동으로 빈 파일을 생성하기
            val out = FileOutputStream(tempFile) // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close() // 스트림 닫아주기
            Toast.makeText(applicationContext, "파일 저장 성공", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "파일 저장 실패", Toast.LENGTH_SHORT).show()
        }
    }

}