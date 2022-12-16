import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.impl.Log

@Composable
fun verticalDivider(height: Float = 24f) {
    Divider(modifier = Modifier.width(1f.dp).height(height.dp))
}

@Composable
fun divider(width: Float, color: Color = Color.DarkGray) {
    Divider(modifier = Modifier.fillMaxWidth(width), color = color)
}
//------------------------------------------------------------------------------
/*
    Преобразование текста в число с плавающей точкой.
    Return: Pair(1,2) | 1) булевое значениея результата, 2) результат
 */
fun String.stringToFloat() = try {
    Pair(true, toFloat())
} catch (_: Exception) {
    Pair(false, 0f)
}
