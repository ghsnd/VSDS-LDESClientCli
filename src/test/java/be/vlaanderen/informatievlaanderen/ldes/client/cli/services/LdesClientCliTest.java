package be.vlaanderen.informatievlaanderen.ldes.client.cli.services;

import be.vlaanderen.informatievlaanderen.ldes.client.cli.model.EndpointBehaviour;
import org.apache.jena.riot.Lang;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class LdesClientCliTest {

	ExecutorService executorService = mock(ExecutorService.class);
	LdesClientCli ldesClientCli = new LdesClientCli(executorService);

	@ParameterizedTest
	@ArgumentsSource(EndpointBehavoirArgumentsProvider.class)
	void when_LdesClientCliIsStarted_TaskOfClassCliRunnerIsSubmitted(final EndpointBehaviour endpointBehaviour) {
		ldesClientCli.start("fragmentId", Lang.NQUADS, Lang.TURTLE, 1000L, 20L, endpointBehaviour);

		verify(executorService, times(1)).submit(any(CliRunner.class));
		verify(executorService, times(1)).shutdown();
	}

	static class EndpointBehavoirArgumentsProvider implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
			return Stream.of(
					Arguments.of(EndpointBehaviour.STOPPING),
					Arguments.of(EndpointBehaviour.WAITING));
		}
	}

}