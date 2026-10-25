package com.tecsup.lab09

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

val LightBlueAccent = Color(0xFF81D4FA)
val DarkBlueText = Color(0xFF0D47A1)
val CardDarkBg = Color(0xFF38343E)
val TextSecondaryGray = Color(0xFFB0AAB8)

@Composable
fun ScreenUsers(navController: NavHostController, servicio: PostApiService) {
    val listaUsers = remember { mutableStateListOf<UserModel>() }

    LaunchedEffect(Unit) {
        val usuarios = servicio.getUsers()
        listaUsers.clear()
        listaUsers.addAll(usuarios)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(listaUsers) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("usersVer/${user.id}") },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardDarkBg)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(LightBlueAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.first().toString(),
                            color = DarkBlueText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = user.name,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "@${user.username}",
                            color = TextSecondaryGray,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email",
                                tint = TextSecondaryGray,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = user.email,
                                color = TextSecondaryGray,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenUserDetail(navController: NavHostController, servicio: PostApiService, id: Int) {
    var user by remember { mutableStateOf<UserModel?>(null) }

    LaunchedEffect(id) {
        user = servicio.getUserById(id)
    }

    user?.let { u ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(LightBlueAccent),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = u.name.first().toString(),
                    color = DarkBlueText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = u.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = "@${u.username}",
                color = TextSecondaryGray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Tarjeta 1: Informacion de Contacto
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardDarkBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Información de Contacto",
                        color = LightBlueAccent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = TextSecondaryGray)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(u.phone, color = Color.White, fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = null, tint = TextSecondaryGray)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(u.email, color = Color.White, fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = TextSecondaryGray)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(u.website, color = LightBlueAccent, fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tarjeta 2: Direccion
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardDarkBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = LightBlueAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Dirección",
                            color = LightBlueAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("${u.address.street}, ${u.address.suite}", color = Color.White, fontSize = 15.sp)
                    Text("${u.address.city}, Zip: ${u.address.zipcode}", color = Color.White, fontSize = 15.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Lat: ${u.address.geo.lat} / Lng: ${u.address.geo.lng}",
                        color = TextSecondaryGray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tarjeta 3: Empresa
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardDarkBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Business, contentDescription = null, tint = LightBlueAccent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Empresa",
                            color = LightBlueAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(u.company.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("\"${u.company.catchPhrase}\"", color = TextSecondaryGray, fontStyle = FontStyle.Italic, fontSize = 14.sp)
                    Text(u.company.bs, color = TextSecondaryGray, fontSize = 13.sp)
                }
            }
        }
    }
}
