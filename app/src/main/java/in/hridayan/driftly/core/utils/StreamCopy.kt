package `in`.hridayan.driftly.core.utils

import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.CancellationException
import java.io.OutputStream

suspend fun ByteReadChannel.copyAndCloseTo(
    output: OutputStream,
    totalBytes: Long,
    onProgress: (Int) -> Unit,
    isCancelled: () -> Boolean
) {
    var bytesCopied = 0L
    val buffer = ByteArray(8 * 1024)  // 8 KB buffer
    var lastPercent = -1

    try {
        while (true) {
            if (isCancelled()) {
                output.close()
                cancel(CancellationException("Cancelled by user"))
                return
            }

            // This is a blocking read: waits until some bytes are read or EOF (-1)
            val read = readAvailable(buffer, 0, buffer.size)

            if (read == -1) break  // EOF reached

            if (read > 0) {
                output.write(buffer, 0, read)
                bytesCopied += read

                if (totalBytes > 0) {
                    val percent = ((bytesCopied.toDouble() / totalBytes) * 100).toInt()
                    if (percent != lastPercent) {
                        onProgress(percent.coerceIn(0, 100))  // Clamp percent safely
                        lastPercent = percent
                    }
                } else {
                    // Unknown total length, can't calculate percent
                    if (lastPercent != -2) {
                        onProgress(-1)  // signal indeterminate progress if needed
                        lastPercent = -2
                    }
                }
            } else {
                // read == 0: no bytes currently available, delay a bit or just continue loop
                // Avoid tight loop that burns CPU - optional delay here
                kotlinx.coroutines.delay(10)
            }
        }
    } finally {
        output.flush()
        output.close()
    }

    // Make sure progress is 100% at the end
    if (lastPercent != 100) {
        onProgress(100)
    }
}
