package com.lyg.conference.repository

import com.lyg.conference.models.Speaker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay

class MockSpeakerRepository : SpeakerRepository {
    
    private val _speakers = MutableStateFlow<List<Speaker>>(getSampleSpeakers())
    
    override suspend fun getAllSpeakers(): Flow<List<Speaker>> {
        return _speakers.asStateFlow()
    }
    
    override suspend fun getSpeakerById(id: String): Speaker? {
        return _speakers.value.find { it.id == id }
    }
    
    override suspend fun createSpeaker(speaker: Speaker): Result<String> {
        return try {
            // Simulate network delay
            delay(1000)
            
            val currentSpeakers = _speakers.value.toMutableList()
            currentSpeakers.add(speaker)
            _speakers.value = currentSpeakers.sortedBy { it.name }
            Result.success(speaker.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateSpeaker(speaker: Speaker): Result<Unit> {
        return try {
            delay(800)
            
            val currentSpeakers = _speakers.value.toMutableList()
            val index = currentSpeakers.indexOfFirst { it.id == speaker.id }
            if (index != -1) {
                currentSpeakers[index] = speaker
                _speakers.value = currentSpeakers.sortedBy { it.name }
                Result.success(Unit)
            } else {
                Result.failure(Exception("Speaker not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteSpeaker(id: String): Result<Unit> {
        return try {
            delay(500)
            
            val currentSpeakers = _speakers.value.toMutableList()
            val removed = currentSpeakers.removeAll { it.id == id }
            if (removed) {
                _speakers.value = currentSpeakers
                Result.success(Unit)
            } else {
                Result.failure(Exception("Speaker not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun uploadSpeakerImage(speakerId: String, imageData: ByteArray): Result<String> {
        return try {
            // Simulate image upload with progress
            delay(2000)
            
            val imageUrl = "https://storage.googleapis.com/lyg-conference/speakers/$speakerId.jpg"
            Result.success(imageUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun getSampleSpeakers(): List<Speaker> {
        return listOf(
            Speaker(
                id = "speaker_1",
                name = "Pastor John Smith",
                title = "Senior Pastor",
                organization = "Louisville Community Church",
                email = "john.smith@lcc.org",
                bio = "Pastor John has been serving in youth ministry for over 15 years and is passionate about developing young Christian leaders. He holds a Master of Divinity from Southern Seminary and has authored several books on youth discipleship.",
                expertise = listOf("Youth Ministry", "Leadership Development", "Biblical Teaching", "Discipleship"),
                socialLinks = mapOf(
                    "linkedin" to "https://linkedin.com/in/johnsmith",
                    "twitter" to "@pastorjohnsmith",
                    "website" to "https://johnsmithministries.com"
                ),
                profileImageUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400&h=400&fit=crop&crop=face"
            ),
            Speaker(
                id = "speaker_2",
                name = "Sarah Johnson",
                title = "Youth Director",
                organization = "Faith Baptist Church",
                email = "sarah.johnson@faithbaptist.org",
                bio = "Sarah specializes in leadership development and has led numerous youth conferences across Kentucky. She is known for her engaging workshops and practical approach to ministry.",
                expertise = listOf("Leadership Training", "Event Planning", "Youth Counseling", "Team Building"),
                socialLinks = mapOf(
                    "linkedin" to "https://linkedin.com/in/sarahjohnson",
                    "instagram" to "@sarahjohnsonministries"
                ),
                profileImageUrl = "https://images.unsplash.com/photo-1494790108755-2616b612b786?w=400&h=400&fit=crop&crop=face"
            ),
            Speaker(
                id = "speaker_3",
                name = "David Wilson",
                title = "Worship Leader & Recording Artist",
                organization = "Grace Fellowship",
                email = "david.wilson@gracefellowship.org",
                bio = "David is a contemporary Christian music artist and worship leader with a heart for youth ministry. His songs have been featured in several Christian albums and he regularly leads worship at youth events.",
                expertise = listOf("Worship Leading", "Music Ministry", "Songwriting", "Audio Production"),
                socialLinks = mapOf(
                    "spotify" to "https://open.spotify.com/artist/davidwilson",
                    "youtube" to "https://youtube.com/@davidwilsonmusic",
                    "instagram" to "@davidwilsonworship"
                ),
                profileImageUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=400&h=400&fit=crop&crop=face"
            ),
            Speaker(
                id = "speaker_4",
                name = "Dr. Emily Davis",
                title = "Licensed Christian Counselor",
                organization = "Hope Counseling Center",
                email = "emily.davis@hopecounseling.org",
                bio = "Dr. Davis specializes in adolescent psychology and Christian counseling, helping young people navigate life's challenges with faith-based guidance. She has a PhD in Clinical Psychology and is a licensed professional counselor.",
                expertise = listOf("Adolescent Psychology", "Christian Counseling", "Mental Health", "Crisis Intervention"),
                socialLinks = mapOf(
                    "linkedin" to "https://linkedin.com/in/dremilydavis",
                    "website" to "https://hopecounselingcenter.org"
                ),
                profileImageUrl = "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=400&h=400&fit=crop&crop=face"
            ),
            Speaker(
                id = "speaker_5",
                name = "Michael Thompson",
                title = "Youth Pastor",
                organization = "Crossroads Community Church",
                email = "mike.thompson@crossroads.org",
                bio = "Mike has been working with teenagers for over 10 years and is passionate about community service and social justice. He leads mission trips and community outreach programs.",
                expertise = listOf("Community Service", "Mission Work", "Social Justice", "Youth Mentoring"),
                socialLinks = mapOf(
                    "twitter" to "@mikethompsoncc",
                    "facebook" to "MikeThompsonMinistries"
                ),
                profileImageUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=400&h=400&fit=crop&crop=face"
            )
        )
    }
}
