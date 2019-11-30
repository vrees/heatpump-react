package de.vrees.heatpump.config;

import de.vrees.heatpump.statemachine.Events;
import de.vrees.heatpump.statemachine.StateMachineEventListener;
import de.vrees.heatpump.statemachine.States;
import de.vrees.heatpump.statemachine.actions.SwitchAllOffAction;
import de.vrees.heatpump.statemachine.actions.SwitchAllOnAction;
import de.vrees.heatpump.statemachine.actions.SwitchCompressorOffAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;

@Configuration
@RequiredArgsConstructor
@EnableStateMachine
@Slf4j
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    private final StateMachineEventListener eventListener;

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
            .initial(States.OFF, switchAllOffAction())
            .state(States.READY, switchAllOffAction())
            .state(States.RUNNING, switchAllOnAction())
            .state(States.BACKLASH, switchCompressorOffAction())
            .state(States.ERROR, switchAllOffAction());
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
            .and()
            .withExternal()
            .source(States.READY).target(States.OFF).event(Events.SWITCH_OFF)
            .and()
            .withExternal()
            .source(States.RUNNING).target(States.ERROR).event(Events.LIMIT_EXCEEDED)
            .and()
            .withExternal()
            .source(States.BACKLASH).target(States.ERROR).event(Events.LIMIT_EXCEEDED)
            .and()
            .withExternal()
            .source(States.ERROR).target(States.OFF).event(Events.ACKNOWLEDGE)
            .and()
            .withExternal()
            .source(States.ERROR).target(States.OFF).event(Events.SWITCH_OFF)

        ;
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return eventListener;
    }

    @Bean
    public Action<States, Events> switchAllOffAction() {
        return new SwitchAllOffAction();
    }

    @Bean
    public Action<States, Events> switchCompressorOffAction() {
        return new SwitchCompressorOffAction();
    }

    @Bean
    public Action<States, Events> switchAllOnAction() {
        return new SwitchAllOnAction();
    }
}
