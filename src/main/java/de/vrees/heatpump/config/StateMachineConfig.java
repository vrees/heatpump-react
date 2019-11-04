package de.vrees.heatpump.config;

import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
@Slf4j
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {


    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
        throws Exception {
        config
            .withConfiguration()
            .autoStartup(true)
            .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
        throws Exception {
        states
            .withStates()
            .initial(States.OFF)
            .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
        throws Exception {
        transitions
            .withExternal()
            .source(States.OFF).target(States.READY).event(Events.SWITCH_ON)
            .and()
            .withExternal()
            .source(States.READY).target(States.RUNNING).event(Events.HEAT_REQUEST)
            .and()
            .withExternal()
            .source(States.RUNNING).target(States.BACKLASH).event(Events.TEMPERATURE_REACHED)
            .and()
            .withExternal()
            .source(States.BACKLASH).target(States.READY).event(Events.COOLDED_DOWN)
        ;
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                log.info("Statemachine: State changed from {} to {}", from != null ? from.getId() : ".", to.getId());
            }
        };
    }
}
