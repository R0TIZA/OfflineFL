package ru.rotiza.offlinefl;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.beta.assistants.*;
import com.openai.models.beta.threads.Thread;
import com.openai.models.beta.threads.messages.Message;
import com.openai.models.beta.threads.messages.MessageCreateParams;
import com.openai.models.beta.threads.messages.MessageListPage;
import com.openai.models.beta.threads.messages.MessageListParams;
import com.openai.models.beta.threads.runs.Run;
import com.openai.models.beta.threads.runs.RunCreateParams;
import com.openai.models.beta.threads.runs.RunRetrieveParams;
import com.openai.models.beta.threads.runs.RunStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OpenAI {
    public static void main(String[] args) throws InterruptedException, IOException {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        Assistant assistant = client.beta()
                .assistants()
                .create(AssistantCreateParams.builder()
                        .name("Помощник")
                        .instructions("Ты персональный универсальный помощник. Напиши код и ответь на любой вопрос.")
                        .addTool(CodeInterpreterTool.builder().build())
                        .model(ChatModel.GPT_4O_MINI)
                        .build());
        Thread thread = client.beta().threads().create();
        String question = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("USER:");
            question = bufferedReader.readLine();
            if(question.equals("quit")||question.equals("выход")) break;

            client.beta()
                    .threads()
                    .messages()
                    .create(MessageCreateParams.builder()
                            .threadId(thread.id())
                            .role(MessageCreateParams.Role.USER)
                            .content(question)
                            .build());

            Run run = client.beta()
                    .threads()
                    .runs()
                    .create(RunCreateParams.builder()
                            .threadId(thread.id())
                            .assistantId(assistant.id())
                            .instructions("Пожалуйсто называй пользователя мистер Михаил. У пользователя есть премиум аккаунт.")
                            .build());
            while (run.status().equals(RunStatus.QUEUED) || run.status().equals(RunStatus.IN_PROGRESS)) {
//                System.out.println("Polling run...");
                java.lang.Thread.sleep(500);
                run = client.beta()
                        .threads()
                        .runs()
                        .retrieve(RunRetrieveParams.builder()
                                .threadId(thread.id())
                                .runId(run.id())
                                .build());
            }
//            System.out.println("Выполнение завершилось со статусом: " + run.status() + "\n");

            if (!run.status().equals(RunStatus.COMPLETED)) {
                return;
            }

            MessageListPage page = client.beta()
                    .threads()
                    .messages()
                    .list(MessageListParams.builder()
                            .threadId(thread.id())
                            .order(MessageListParams.Order.DESC)
                            .build());

            Message currentMessage = (Message)page.autoPager().stream().toArray()[0];
            System.out.println(currentMessage.role().toString().toUpperCase());
            currentMessage.content().stream()
                    .flatMap(content -> content.text().stream())
                    .forEach(textBlock -> System.out.println(textBlock.text().value()));
            System.out.println();
        }

        AssistantDeleted assistantDeleted = client.beta()
                .assistants()
                .delete(AssistantDeleteParams.builder()
                        .assistantId(assistant.id())
                        .build());
        System.out.println("Ассистент удален: " + assistantDeleted.deleted());
    }
}
