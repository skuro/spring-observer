/*
 * Copyright (c) 2013 Carlo Sciolla
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package tk.skuro.spring.processor;

import tk.skuro.spring.observable.SpringObservable;
import tk.skuro.spring.observer.SpringObserver;

import java.util.Observer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Automatically registers instances of
 * @author Carlo Sciolla
 * @added 2013-03-09
 */
@Component
@Scope("prototype")
public class ObservablePostProcessor implements BeanPostProcessor {

    private final SpringObservable observable;

    public ObservablePostProcessor(SpringObservable observable) {
        this.observable = observable;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        // NOP
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        Class<? extends Observer> observerClass = observable.observerClass();
        if (observerClass.isAssignableFrom(o.getClass())) {
            // register it!
            observable.addObserver((SpringObserver) o);
        }

        // leave untouched
        return o;
    }
}
