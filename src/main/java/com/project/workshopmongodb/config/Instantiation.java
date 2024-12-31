package com.project.workshopmongodb.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.project.workshopmongodb.domain.Post;
import com.project.workshopmongodb.domain.User;
import com.project.workshopmongodb.dto.AuthorDTO;
import com.project.workshopmongodb.dto.CommentDTO;
import com.project.workshopmongodb.repository.PostRepository;
import com.project.workshopmongodb.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");

        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("21/03/2028"), "Partiu viagem!", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2028"), new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1));

        postRepository.saveAll(Arrays.asList(post1));

        maria.getPosts().addAll(Arrays.asList(post1));
        userRepository.save(maria);
    }
    
}
