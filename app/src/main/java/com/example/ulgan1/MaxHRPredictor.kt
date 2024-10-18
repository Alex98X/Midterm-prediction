package com.example.ulgan1

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.io.FileInputStream
import java.io.IOException

class MaxHRPredictor(private val context: Context) {
    private var interpreter: Interpreter? = null

    init {
        interpreter = Interpreter(loadModelFile())
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("linear_regression_model-MaxHR.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun predictMaxHR(age: Float): Float {
        val input = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder())
        input.putFloat(age)

        val output = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder())
        interpreter?.run(input, output)
        output.rewind()

        return output.float
    }
}
